# Usa uma imagem base do Maven para compilar o projeto
FROM maven:3.8.7-eclipse-temurin-19-alpine AS build

LABEL maintainer="Gabriel"

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto
COPY . .

# Compila o projeto com Maven
RUN mvn clean package -DskipTests

# Usa uma imagem menor para rodar a aplicação
FROM eclipse-temurin:19-jre-alpine

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR gerado na etapa de build
COPY --from=build /app/target/acervofacil-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]
