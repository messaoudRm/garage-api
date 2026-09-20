#!/usr/bin/env bash
# Navigation entre les exercices du cours et le projet.
# Dans le conteneur : bash infra/workbench.sh list|open <n> [save]|projet|save
#
# Le principe tient en une phrase : tout ce qui touche à src/ gare d'abord ce
# qui s'y trouve. Rien ne se perd, quel que soit l'ordre des commandes.
#
#   un exercice  → plusieurs sauvegardes, horodatées : on peut recommencer
#   le projet    → une seule place de garage : on le retrouve tel qu'on l'a laissé
set -u

EXERCISES=exercises
BENCH=.workbench
CURRENT="$BENCH/current"
GARAGE="$BENCH/garage/projet"
SAVES="$BENCH/saves"
SOURCES=src

# Le conteneur tourne en « ubuntu » (uid 1000) ; sur un Linux où le dépôt
# appartient à un autre uid, il ne pourrait pas écrire dans le montage. Le
# Makefile nous lance donc en root, et on rend les fichiers à leur propriétaire
# en sortant — sinon l'élève ne pourrait plus les éditer dans son IDE.
OWNER=$(stat -c '%u:%g' . 2>/dev/null || echo '')
give_back() {
  [ -n "$OWNER" ] || return 0
  chown -R "$OWNER" "$SOURCES" "$BENCH" 2>/dev/null || true
}
trap give_back EXIT

# --- petits outils ------------------------------------------------------------

# Deux familles : « 3 » → exercises/03-…, « b1 » → exercises/b1-….
# Les numéros suivent le cours du jour 1 ; les b sont la réserve du jour 2.
exercise_dir() {
  local key="$1" prefix printed
  case "$key" in
    b[0-9]*) prefix="$key" ;;
    [0-9]*) prefix=$(printf '%02d' "$key" 2>/dev/null) || return 1 ;;
    *) return 1 ;;
  esac
  printed=$(find "$EXERCISES" -maxdepth 1 -type d -name "$prefix-*" 2>/dev/null | sort | head -1)
  [ -n "$printed" ] || return 1
  echo "$printed"
}

# Le slot d'une clé : 3 → exo3, b1 → bonus1.
slot_for() {
  case "$1" in
    b[0-9]*) echo "bonus${1#b}" ;;
    *) echo "exo$((10#$1))" ;;
  esac
}

command_for() {
  case "$1" in
    b[0-9]*) echo "make bonus${1#b}" ;;
    *) echo "make exo$((10#$1))" ;;
  esac
}

title_of() {
  head -1 "$1/brief.txt" 2>/dev/null || basename "$1"
}

# printf compte des octets : sans ça, un titre accentué décale la colonne.
pad() {
  local text="$1" width="$2" length
  length=$(printf '%s' "$text" | wc -m)
  printf '%s%*s' "$text" "$(( width > length ? width - length : 0 ))" ''
}

slot_now() {
  [ -f "$CURRENT" ] && head -1 "$CURRENT" || echo projet
}

remember() {
  mkdir -p "$BENCH"
  echo "$1" > "$CURRENT"
}

# Y a-t-il autre chose que les .gitkeep dans src/ ?
sources_present() {
  [ -n "$(find "$SOURCES" -type f ! -name '.gitkeep' -print -quit 2>/dev/null)" ]
}

copy_sources_to() {
  local destination="$1"
  mkdir -p "$destination"
  cp -R "$SOURCES/." "$destination/"
}

clear_sources() {
  rm -rf "${SOURCES:?}/main/java" "${SOURCES:?}/test/java" "${SOURCES:?}/test/resources"
  mkdir -p "$SOURCES/main/java" "$SOURCES/test/java"
  touch "$SOURCES/main/java/.gitkeep" "$SOURCES/test/java/.gitkeep"
}

# --- garer ---------------------------------------------------------------------

# Range ce qui est dans src/ à la place qui revient au slot courant.
park() {
  local slot stamp destination
  slot=$(slot_now)
  sources_present || return 0
  if [ "$slot" = projet ]; then
    # Une seule place : on remplace, on n'empile pas.
    rm -rf "$GARAGE"
    copy_sources_to "$GARAGE"
    echo "→ projet garé."
  else
    # Pas de « : » dans le nom : ce dossier vit aussi sur un disque Windows.
    stamp=$(date +%Y-%m-%d_%H-%M-%S)
    destination="$SAVES/$slot/$stamp"
    copy_sources_to "$destination"
    echo "→ $slot sauvegardé ($stamp)."
  fi
}

saves_of() {
  find "$SAVES/$1" -maxdepth 1 -mindepth 1 -type d 2>/dev/null | sort
}

# --- les commandes -------------------------------------------------------------

list_family() {  # $1 motif des dossiers, $2 titre de la section
  local slot directory key command saved
  slot=$(slot_now)
  [ -n "$(find "$EXERCISES" -maxdepth 1 -mindepth 1 -type d -name "$1" 2>/dev/null)" ] || return 0
  echo "$2"
  echo
  for directory in $(find "$EXERCISES" -maxdepth 1 -mindepth 1 -type d -name "$1" 2>/dev/null | sort); do
    key=$(basename "$directory" | cut -d- -f1)
    command=$(command_for "$key")
    saved=$(slot_for "$key")
    printf '  %-13s %s %s%s\n' \
      "$command" \
      "$(pad "$(title_of "$directory")" 40)" \
      "$(saves_of "$saved" | wc -l | tr -d ' ') sauvegarde(s)" \
      "$([ "$slot" = "$saved" ] && echo '   ← ici')"
  done
  echo
}

do_list() {
  local slot
  slot=$(slot_now)
  list_family '[0-9]*' "Pendant le cours — un exercice par principe :"
  list_family 'b[0-9]*' "En réserve, si vous êtes en avance :"
  printf '  %-13s %s %s\n' "make projet" "$(pad "Votre projet" 40)" \
    "$([ -d "$GARAGE" ] && echo 'au garage' || echo 'pas encore commencé')$([ "$slot" = projet ] && echo '   ← ici')"
  echo
  echo "make save             sauvegarde sans changer d'exercice"
  echo "make exo2 restore=1   revient à votre sauvegarde n° 1 de l'exercice 2"
}

do_open() {
  local key="$1" restore="${2:-}" directory slot chosen list
  directory=$(exercise_dir "$key") || { echo "Pas d'exercice « $key ». Tape make exos." >&2; return 1; }
  slot=$(slot_for "$key")

  if [ -n "$restore" ]; then
    mapfile -t list < <(saves_of "$slot")
    chosen="${list[$((restore - 1))]:-}"
    if [ -z "$chosen" ]; then
      echo "Pas de sauvegarde n° $restore pour $slot." >&2
      [ "${#list[@]}" -gt 0 ] && printf '  %s\n' "${list[@]}" >&2
      return 1
    fi
  fi

  park
  clear_sources
  if [ -n "$restore" ]; then
    cp -R "$chosen/." "$SOURCES/"
    remember "$slot"
    echo "↩ $slot restauré depuis $(basename "$chosen")."
  else
    cp -R "$directory/src/." "$SOURCES/"
    remember "$slot"
    echo "▶ $(title_of "$directory")"
  fi
  echo
  # Un test rouge est une issue normale : on ouvre un exercice, on ne le valide pas.
  bash infra/sandbox.sh test || true
  echo
  [ -f "$directory/brief.txt" ] && tail -n +2 "$directory/brief.txt"
  echo
  # make dev sert le projet : il lance aussi le programme et ouvre une page web,
  # qui n'a rien à dire sur un exercice. Ici, seule la boucle des tests compte.
  echo "Gardez « make test watch=1 » ouvert dans un autre terminal :"
  echo "les tests se relancent à chaque sauvegarde."
}

do_projet() {
  [ "$(slot_now)" = projet ] && { echo "Vous êtes déjà sur le projet."; return 0; }
  park
  clear_sources
  if [ -d "$GARAGE" ]; then
    cp -R "$GARAGE/." "$SOURCES/"
    echo "▶ Projet repris là où vous l'aviez laissé."
  else
    echo "▶ Projet démarré : src/ est vierge, à vous d'écrire."
  fi
  remember projet
}

do_save() {
  sources_present || { echo "Rien à sauvegarder : src/ est vide."; return 0; }
  park
}

case "${1:-}" in
  list) do_list ;;
  open) shift; do_open "$@" ;;
  projet) do_projet ;;
  save) do_save ;;
  *) echo "usage : bash infra/workbench.sh list|open <n> [save]|projet|save" >&2; exit 2 ;;
esac
