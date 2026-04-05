# ================================================================
# LibraFlow — Multi-Stage Dockerfile
#
# Stages:
#   deps    – baixa dependências Maven (cache invalidado só ao mudar pom.xml)
#   test    – compila e roda unit tests (testes Testcontainers são excluídos
#             pois precisam de Docker daemon; rodar via CI com socket montado)
#   build   – empacota o fat JAR (DskipTests, validação já feita no stage test)
#   runtime – imagem final mínima com JRE
#
# Build padrão:
#   docker build -t libraflow .
#
# Build pulando testes:
#   docker build --build-arg SKIP_TESTS=true -t libraflow .
#
# Rodar só os testes (com suporte a Testcontainers via socket):
#   docker build --target test \
#     --build-arg SKIP_TESTS=false \
#     --build-arg DOCKER_HOST=unix:///var/run/docker.sock \
#     -t libraflow-test .
# ================================================================

# ----------------------------------------------------------------
# Stage 1 — Dependency cache
# Copia apenas pom.xml para que o cache dessa camada só seja
# invalidado quando as dependências mudarem.
# ----------------------------------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS deps

WORKDIR /app

COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

RUN chmod +x mvnw && \
    ./mvnw dependency:go-offline --no-transfer-progress -B

# ----------------------------------------------------------------
# Stage 2 — Test
# Roda testes que não dependem de infraestrutura externa.
# LibraflowApplicationTests usa Testcontainers (PostgreSQL) e é
# excluída aqui. Para rodá-la, usar CI com Docker socket montado.
# ----------------------------------------------------------------
FROM deps AS test

ARG SKIP_TESTS=false

COPY src/ src/

RUN ./mvnw test --no-transfer-progress -B \
    -DskipTests=${SKIP_TESTS} \
    -Dsurefire.excludes="**/LibraflowApplicationTests.java"

# ----------------------------------------------------------------
# Stage 3 — Build
# Empacota o fat JAR. Testes já foram validados no stage anterior.
# ----------------------------------------------------------------
FROM deps AS build

COPY src/ src/

RUN ./mvnw package --no-transfer-progress -B -DskipTests \
    # Remove devtools e docker-compose do JAR final (são optional,
    # mas garantimos que não quebrem a imagem de produção)
    && echo "Build concluído: $(ls -lh target/*.jar)"

# ----------------------------------------------------------------
# Stage 4 — Runtime
# Imagem final com apenas o JRE. Sem Maven, sem código-fonte,
# sem ferramentas de build.
# ----------------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS runtime

LABEL org.opencontainers.image.title="LibraFlow" \
      org.opencontainers.image.description="Sistema de gerenciamento de biblioteca" \
      org.opencontainers.image.version="0.0.1-SNAPSHOT" \
      org.opencontainers.image.authors="gkaram"

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# Configurações de JVM otimizadas para containers:
#   UseContainerSupport  – respeita os limites de memória do container (cgroups)
#   MaxRAMPercentage     – usa até 75% da RAM disponível para o heap
#   docker.compose       – desativa o spring-boot-docker-compose (que tenta subir
#                          o compose.yaml automaticamente em dev)
ENTRYPOINT ["java", \
    "-XX:+UseContainerSupport", \
    "-XX:MaxRAMPercentage=75.0", \
    "-Dspring.docker.compose.enabled=false", \
    "-jar", "app.jar"]
