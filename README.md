# Java & programmation orientée objet

Ton éditeur, tes extensions, ton IA : tout reste sur ta machine.
La compilation et les tests tournent dans un conteneur : même résultat chez toi, chez tes coéquipiers et chez le formateur.

## Avant le premier cours — 15 minutes

| | Windows | Mac | Linux |
|---|---|---|---|
| **Docker** | [Docker Desktop](https://docs.docker.com/desktop/setup/install/windows-install/) | [Docker Desktop](https://docs.docker.com/desktop/setup/install/mac-install/) | [Docker Engine](https://docs.docker.com/engine/install/) + plugin compose |
| **make** | `winget install ezwinports.make` | `xcode-select --install` | `sudo apt install make` |
| **git** | [Git for Windows](https://git-scm.com/downloads/win) | déjà là avec `xcode-select` | `sudo apt install git` |
| **Ton éditeur** | IntelliJ IDEA, VS Code… celui que tu préfères | idem | idem |

Puis :

```bash
git clone <l'adresse de ton fork>
cd <le dossier>
make install
```

`make install` doit finir par **Installation OK**. Le dépôt est vierge : « Aucun test trouvé » juste avant, c'est normal. Sinon, regarde le [dépannage](#dépannage).

Ouvre ensuite le dossier dans ton éditeur. Il reconnaît le projet grâce à `pom.xml` ; s'il réclame un JDK, accepte d'en installer un en **version 21** (ou 25 si tu l'as déjà).
- **IntelliJ IDEA** : *File → Open*, puis *Download JDK* si demandé.
- **VS Code** : extension *Extension Pack for Java*, puis *Install JDK* si demandé.

## Coder

Le dépôt est vide : crée ta première classe dans un package sous `src/main/java` (voir [Où écrire](#où-écrire)).

Dans le terminal de ton éditeur :

```bash
make dev
```

À chaque sauvegarde, même d'un fichier que tu viens de créer :
1. le code est recompilé ;
2. les tests sont rejoués ;
3. ton programme (la classe qui contient `main`), s'il y en a un, est relancé, et sa sortie s'affiche en dessous.

C'est ce résultat qui fait foi, pas le bouton de ton IDE.

Pour les tests seuls, en continu : `make test watch=1`.

### Une application web

Si ton programme est un serveur web, fais-le écouter sur le port **8080** (par exemple avec `HttpServer` du JDK), puis ouvre **http://localhost:2044** pendant `make dev` :
- la page se recharge toute seule à chaque sauvegarde ;
- si le code ne compile pas, l'erreur s'affiche dans la page ;
- dès que tu corriges, ta page revient.

8080 dans ton code, 2044 dans ton navigateur : entre les deux, `make dev` place le relais qui recharge la page.

Pourquoi 2044 ? T est la 20ᵉ lettre de l'alphabet, D la 4ᵉ : **TDD**.

Un programme qui lit le clavier (`Scanner`) se lance avec `make run`, pas avec `make dev`.

## Où écrire

```
src/main/java/<package>/   ton code métier
src/test/java/<package>/   tes tests
pom.xml                    la liste des bibliothèques, lue par ton éditeur — tu n'as pas à y toucher
infra/                     la machinerie — tu n'as pas à y toucher
```

**Toute classe vit dans un package** (un dossier sous `src/main/java` ou `src/test/java`), jamais à la racine.

Un test se nomme comme une phrase, avec des `_` à la place des espaces : `deposits_add_up` s'affiche « deposits add up ».

Les fichiers `.gitkeep` gardent les dossiers vides dans git : supprime-les quand tu veux.

## Commandes

| Commande | Effet |
|---|---|
| `make install` | construit l'environnement et lance les tests — une seule fois |
| `make build` | reconstruit tout et lance les tests, comme sur un clone neuf |
| `make dev` | à chaque sauvegarde : recompile, relance les tests, le programme et la page web |
| `make test` | lance les tests une fois |
| `make test watch=1` | relance les tests à chaque sauvegarde |
| `make run` | lance ton programme une fois, clavier compris : la classe qui contient `public static void main` |
| `make jshell` | ouvre JShell avec tes classes déjà importées |
| `make terminal` | ouvre un terminal dans le conteneur |
| `make stop` | coupe le conteneur |
| `make uninstall` | supprime le conteneur et son image |

## La console du projet

`front/console.html` s'ouvre d'un double-clic dans ton navigateur. Elle envoie à **ton** serveur les
cas du client, un par bouton, avec le résultat attendu écrit à côté : tu compares, tu corriges.

Lance `make dev` d'abord — ton API doit répondre sur le port 8080. Si la console dit
« injoignable » alors que `curl` marche, il manque un en-tête à tes réponses :

```java
echange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
```

Sans lui, le navigateur reçoit ta réponse et la jette sans rien afficher d'autre qu'une erreur
réseau. C'est la panne la plus déroutante de la semaine, et elle tient en une ligne.

Le formateur évalue ton projet en clonant ton dépôt puis en lançant `make build`. Un fichier créé mais oublié dans `git add` : chez toi tout est vert, chez lui le projet ne compile pas.

## Dépannage

| Symptôme | Solution |
|---|---|
| `make` introuvable (Windows) | `winget install ezwinports.make`, puis ferme et rouvre le terminal |
| `Cannot connect to the Docker daemon`, ou `dockerDesktopLinuxEngine` introuvable | Docker Desktop n'est pas lancé : ouvre-le et attends *Engine running* |
| Docker Desktop réclame WSL (Windows) | `wsl --install` dans un terminal administrateur, puis redémarre |
| Docker Desktop réclame la virtualisation | active-la dans le BIOS (*Intel VT-x* ou *AMD-V*) |
| Linux : `permission denied` sur `/var/run/docker.sock` | `sudo usermod -aG docker $USER`, puis déconnecte-toi et reconnecte-toi |
| `make` ou `docker` introuvable dans le terminal de l'IDE | redémarre ton IDE après les avoir installés |
| L'IDE souligne tout en rouge | choisis un JDK 21 ou plus récent dans les réglages du projet |
| `mvn test` ou le bouton de l'IDE ne donne pas le même résultat | la référence, c'est `make test` |
| `make dev` : « port is already allocated » ou « ports are not available » | le port 2044 est déjà pris sur ta machine (ou un autre `make dev` tourne) : crée un fichier `.env` à la racine avec `PORT_WEB=9090`, puis ouvre http://localhost:9090 |
| `make run` : « Aucune classe avec public static void main » | crée une classe qui contient `public static void main(String[] args)`, dans un package sous `src/main/java` |
| `make dev` : « Plusieurs classes avec public static void main » | garde une seule classe avec `main` |
| `make dev` ne relance rien | vérifie que le fichier est sous `src/` et bien sauvegardé ; la détection prend une seconde |
