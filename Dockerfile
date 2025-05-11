# Etapa 1: Compilación usando Maven
FROM maven:3.8.5-jdk-11 AS build
WORKDIR /app
# Copia el archivo pom.xml y la carpeta de fuentes; se asume que la estructura es la convencional.
COPY pom.xml .
COPY src ./src
# Construye el proyecto y omite la ejecución de tests (opcional)
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecución con JRE
FROM openjdk:11-jre-slim
WORKDIR /app
# Copia el JAR generado desde la etapa "build". Nota: se usa la ruta completa dentro del contenedor build.
COPY --from=build /app/target/TrabajoTaller-1.0.0.jar app.jar
# Expone el puerto en el que la aplicación se ejecuta (en este ejemplo, 8090)
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=8090"]

