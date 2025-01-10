# Utiliser une image de base contenant Maven et OpenJDK
FROM maven:3.9.4-eclipse-temurin-17 as builder

# Définir le répertoire de travail pour la compilation
WORKDIR /app

# Copier les fichiers de configuration Maven et les sources
COPY pom.xml ./
COPY src ./src

# Compiler le projet sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape finale : Utiliser une image légère pour exécuter l'application
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré depuis l'étape précédente
COPY --from=builder /app/target/recipe-service-*.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 8081

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
