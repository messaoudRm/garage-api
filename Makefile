# Toutes les commandes du cours : tape « make » pour les voir.
# Uniquement des appels à docker, sans syntaxe de shell : le même fichier
# marche sous PowerShell (cmd.exe), bash et zsh. La logique vit dans infra/.

# Git Bash réécrit les chemins qui commencent par / avant de les passer à docker.
export MSYS_NO_PATHCONV = 1

COMPOSE = docker compose --project-directory . -f infra/compose.yml

# Sans terminal (CI), docker ne doit pas en réclamer un.
ifdef CI
EXEC = $(COMPOSE) exec -T dev
BENCH = $(COMPOSE) exec -T --user root dev
WEB = $(COMPOSE) run --rm -T --service-ports web
else
EXEC = $(COMPOSE) exec dev
BENCH = $(COMPOSE) exec --user root dev
WEB = $(COMPOSE) run --rm --service-ports web
endif

# make test watch=1 : les tests en continu.
TEST_MODE = $(if $(watch),watch,test)

.PHONY: help install build dev test run jshell terminal stop uninstall start exos projet save

help:
	@echo make install   - installe tout et lance les tests, une seule fois
	@echo make build     - reconstruit tout et lance les tests, comme sur un clone neuf
	@echo make dev       - quand le code change : recompile, relance tests, programme et page web
	@echo make test      - lance les tests une fois, ou en continu avec watch=1
	@echo make run       - lance la classe qui contient main
	@echo make jshell    - ouvre JShell avec tes classes
	@echo make terminal  - ouvre un terminal dans le conteneur
	@echo make stop      - coupe le conteneur
	@echo make uninstall - supprime le conteneur et son image
	@echo .
	@echo Les exercices du cours :
	@echo make exos      - la liste des exercices, et ou vous en etes
	@echo make exo2      - ouvre l exercice 2 : remet son code de depart et lance son test
	@echo "                 (pendant un exercice : make test watch=1, pas make dev)"
	@echo make bonus1    - les exercices en reserve, pour aller plus loin
	@echo make projet    - revient a votre projet, tel que vous l aviez laisse
	@echo make save      - sauvegarde votre travail sans changer d exercice
	@echo .
	@echo Changer d exercice sauvegarde toujours le precedent : rien ne se perd.
	@echo make exo2 restore=1 - revient a votre sauvegarde numero 1 de l exercice 2

install: build
	@echo Installation OK. Ouvre le dossier dans ton IDE, puis lance make dev

build:
	$(COMPOSE) build
	$(COMPOSE) up -d
	$(EXEC) bash infra/sandbox.sh test

dev:
	$(WEB) bash infra/sandbox.sh dev

test: start
	$(EXEC) bash infra/sandbox.sh $(TEST_MODE)

run: start
	$(EXEC) bash infra/sandbox.sh run

jshell: start
	$(EXEC) bash infra/sandbox.sh jshell

terminal: start
	$(EXEC) bash

stop:
	$(COMPOSE) stop

uninstall:
	$(COMPOSE) down --rmi all

# Les commandes d'exercices ecrivent dans src/ : elles passent par root, et le
# script rend les fichiers a leur proprietaire avant de sortir.
exos: start
	$(EXEC) bash infra/workbench.sh list

# Pas de fichier qui s'appelle exo2 : la regle de motif ne peut pas etre court-circuitee.
exo%: start
	$(BENCH) bash infra/workbench.sh open $* $(restore)

bonus%: start
	$(BENCH) bash infra/workbench.sh open b$* $(restore)

projet: start
	$(BENCH) bash infra/workbench.sh projet

save: start
	$(BENCH) bash infra/workbench.sh save

start:
	@$(COMPOSE) up -d
