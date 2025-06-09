# ===========================
# Etapa 1: Build con Gradle
# ===========================
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /nostel

# 1) Copiamos lo mínimo para cachear dependencias:
COPY gradlew .
COPY gradle/ gradle/
COPY build.gradle .
COPY settings.gradle .

# 2) Damos permisos y descargamos dependencias:
RUN chmod +x ./gradlew
RUN ./gradlew --no-daemon dependencies

# 3) Ahora sí copiamos el código fuente y compilamos:
COPY src/ src/
RUN ./gradlew clean bootJar --no-daemon -x test

# ================================
# Etapa 2: Imagen ligera de producción
# ================================
FROM eclipse-temurin:21-jre-jammy
WORKDIR /nostel

# Copiamos únicamente el JAR ya compilado
COPY --from=build /nostel/build/libs/ProyectoNostel-0.0.1-SNAPSHOT.jar nostel.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "nostel.jar"]
