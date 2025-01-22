# Utiliser une image de base Maven avec OpenJDK
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Définir le répertoire de travail pour le build
WORKDIR /app

# Copier les fichiers du projet dans le conteneur
COPY pom.xml .
COPY src ./src

# Construire le projet avec Maven
RUN mvn clean package -DskipTests

# Étape finale : utiliser une image légère pour exécuter l'application
FROM eclipse-temurin:17-jdk-alpine

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape de build
COPY --from=build /app/target/userpreferences-service-*.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 8080

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
