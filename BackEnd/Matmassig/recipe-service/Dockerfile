# Utiliser une image de base OpenJDK
FROM openjdk:17-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier jar de l'application dans le conteneur
COPY target/recipe-service-*.jar app.jar

# Exposer le port utilisé par l'application
EXPOSE 8081

# Commande pour démarrer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
