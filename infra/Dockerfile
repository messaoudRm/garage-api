# Contexte de construction : la racine du dépôt, réduite à pom.xml par Dockerfile.dockerignore.
FROM maven:3.9-eclipse-temurin-21 AS dependencies
WORKDIR /deps
COPY pom.xml .
RUN mvn -q -B dependency:copy-dependencies -DoutputDirectory=/deps/lib

FROM eclipse-temurin:21-jdk
COPY --from=dependencies /deps/lib /opt/sandbox/lib
ENV LANG=C.UTF-8 \
    TZ=Europe/Paris
USER ubuntu
WORKDIR /workspace
