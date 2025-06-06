# ===========================
# Etapa 1: Build con Gradle
# ===========================
FROM eclipse-temurin:21-jdk-jammy AS build

# (Opcional) Cambiamos el directorio de trabajo a /nostel en lugar de /app
WORKDIR /nostel

COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .
COPY src/ src/

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar --no-daemon

# ================================
# Etapa 2: Imagen ligera de producción
# ================================
FROM eclipse-temurin:21-jre-jammy

# Cambiamos también el directorio de trabajo a /nostel
WORKDIR /nostel

# Renombramos el JAR final a nostel.jar en lugar de app.jar
COPY --from=build /nostel/build/libs/*.jar nostel.jar

EXPOSE 8080

# ENTRYPOINT ejecuta nostel.jar en lugar de app.jar
ENTRYPOINT ["java", "-jar", "nostel.jar"]
