#!/usr/bin/env bash
# Compile et lance les tests sans outil de build : javac + lanceur JUnit.
# Dans le conteneur : bash infra/sandbox.sh test|watch|dev|run|jshell
set -u

LIBS=/opt/sandbox/lib
INFRA=$(cd "$(dirname "$0")" && pwd)
SOURCES=src/main/java
TESTS=src/test/java
RESOURCES=src/test/resources
APP_PORT=8080
PROXY_PORT=8000
# Un dossier par processus : make dev et make test peuvent tourner en même temps.
OUT=$(mktemp -d /tmp/sandbox.XXXXXX)
VERSION_FILE="$OUT/version"
ERRORS_FILE="$OUT/errors"
APP_PID=""
PROXY_PID=""
trap 'stop_app; [ -n "$PROXY_PID" ] && kill "$PROXY_PID" 2>/dev/null; rm -rf "$OUT"' EXIT

compile_dir() {
  local source="$1" destination="$2" classpath="$3" files=()
  mkdir -p "$destination"
  [ -d "$source" ] && mapfile -t files < <(find "$source" -type f -name '*.java')
  [ ${#files[@]} -eq 0 ] && return 0
  javac -encoding UTF-8 -Xmaxerrs 5 -d "$destination" -cp "$classpath" "${files[@]}"
}

compile_all() {
  rm -rf "$OUT/classes" "$OUT/test-classes"
  # Le code métier compile sans les bibliothèques de test, comme dans l'IDE.
  if ! compile_dir "$SOURCES" "$OUT/classes" "$OUT/classes" \
    || ! compile_dir "$TESTS" "$OUT/test-classes" "$OUT/classes:$LIBS/*"; then
    echo
    echo "✗ Le code ne compile pas : corrige l'erreur ci-dessus."
    return 1
  fi
  if [ -d "$RESOURCES" ]; then cp -r "$RESOURCES"/. "$OUT/test-classes/"; fi
}

run_tests() {
  # shellcheck disable=SC2016 # le $ appartient au nom de la classe Java, ce n'est pas une variable
  local options=(
    --disable-banner
    --details=tree
    "--scan-class-path=$OUT/test-classes"
    '--config=junit.jupiter.displayname.generator.default=org.junit.jupiter.api.DisplayNameGenerator$ReplaceUnderscores'
  )
  [ -t 1 ] || options+=(--disable-ansi-colors)
  java -cp "$OUT/classes:$OUT/test-classes:$LIBS/*" \
    org.junit.platform.console.ConsoleLauncher execute "${options[@]}" 2>&1 | summarize
  return "${PIPESTATUS[0]}"
}

compile_and_test() {
  compile_all && run_tests
}

# Remplace le tableau de compteurs de JUnit par une ligne.
summarize() {
  awk '
    /^\[ +[0-9]+ tests successful/ { green = $2; next }
    /^\[ +[0-9]+ tests (failed|aborted)/ { red += $2; next }
    /^\[ +[0-9]+ (tests|containers) / || /^Test run finished after/ { next }
    /^ +MethodSource \[/ || /^ +java\.base\// { next }
    /^[[:space:]]*$/ { if (!blank) print ""; blank = 1; next }
    { print; blank = 0 }
    END {
      if (green + red == 0) print "Aucun test trouvé."
      else if (red == 0) printf "✓ Tout est vert : %d test(s)\n", green
      else printf "✗ %d test(s) en échec, %d au vert\n", red, green
    }'
}

# Affiche la classe qui contient main ; échoue s'il n'y en a pas exactement une.
# quiet : rien à dire s'il n'y en a aucune (make dev sur un dépôt qui démarre).
main_class() {
  local mode="${1:-}" mains=()
  mapfile -t mains < <(grep -rlE 'static[[:space:]]+void[[:space:]]+main[[:space:]]*\(' "$SOURCES" 2>/dev/null)
  case ${#mains[@]} in
    1) ;;
    0) [ "$mode" = quiet ] || echo "Aucune classe avec « public static void main » dans $SOURCES." >&2
       return 1 ;;
    *) echo "Plusieurs classes avec « public static void main », il en faut une seule :" >&2
       printf '  %s\n' "${mains[@]}" >&2
       return 1 ;;
  esac
  local class=${mains[0]#"$SOURCES/"}
  class=${class%.java}
  echo "${class//\//.}"
}

run_program() {
  compile_all || return 1
  local class
  class=$(main_class) || return 1
  java -cp "$OUT/classes" "$class"
}

start_app() {
  local class
  class=$(main_class quiet) || return 0
  echo "── programme $class ──"
  java -cp "$OUT/classes" "$class" < /dev/null &
  APP_PID=$!
  # Un serveur : on attend qu'il écoute. Un programme simple : on attend qu'il finisse.
  for _ in $(seq 50); do
    kill -0 "$APP_PID" 2>/dev/null || break
    (exec 3<> "/dev/tcp/127.0.0.1/$APP_PORT") 2>/dev/null && break
    sleep 0.1
  done
}

stop_app() {
  [ -z "$APP_PID" ] && return 0
  kill "$APP_PID" 2>/dev/null
  wait "$APP_PID" 2>/dev/null
  APP_PID=""
}

announce_new_version() {
  date +%s%N > "$VERSION_FILE"
}

full_pass() {
  stop_app
  if ! compile_all > "$ERRORS_FILE" 2>&1; then
    cat "$ERRORS_FILE"
    announce_new_version
    return
  fi
  : > "$ERRORS_FILE"
  run_tests
  echo
  start_app
  announce_new_version
  echo
  echo "Page web : http://localhost:${PORT_WEB:-2044}"
}

# Somme du contenu de src/ : le polling marche là où les notifications de fichiers
# ne traversent pas le montage (code sur un disque Windows).
fingerprint() {
  find src -type f -print0 2>/dev/null | sort -z | xargs -0 -r md5sum | md5sum
}

# $1 vide : les tests seulement. $1 = with-app : tests, programme et page web.
watch_sources() {
  local with_app="${1:-}" previous="" current
  trap 'echo; exit 0' INT TERM
  if [ -n "$with_app" ]; then
    : > "$ERRORS_FILE"
    announce_new_version
    java "$INFRA/ReloadProxy.java" "$PROXY_PORT" "$APP_PORT" "$VERSION_FILE" "$ERRORS_FILE" &
    PROXY_PID=$!
  fi
  while true; do
    current=$(fingerprint)
    if [ "$current" != "$previous" ]; then
      previous=$current
      [ -t 1 ] && printf '\033[2J\033[H'
      echo "── $(date +%H:%M:%S) ──"
      if [ -n "$with_app" ]; then full_pass; else compile_and_test; fi
      echo
      echo "En attente d'une sauvegarde… (Ctrl+C pour quitter)"
    fi
    sleep 1
  done
}

start_jshell() {
  compile_all || return 1
  local imports="$OUT/imports.jsh"
  find "$OUT/classes" -mindepth 2 -name '*.class' -printf '%h\n' | sort -u \
    | sed "s|^$OUT/classes/||; s|/|.|g; s|.*|import &.*;|" > "$imports"
  echo "Tes classes sont chargées. Tape /exit pour sortir."
  jshell --class-path "$OUT/classes" --startup DEFAULT --startup "$imports"
}

case "${1:-}" in
  test) compile_and_test ;;
  watch) watch_sources ;;
  dev) watch_sources with-app ;;
  run) run_program ;;
  jshell) start_jshell ;;
  *) echo "usage : bash infra/sandbox.sh test|watch|dev|run|jshell" >&2; exit 2 ;;
esac
