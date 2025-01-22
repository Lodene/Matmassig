# Étape 1: Utiliser une image de Node.js officielle
FROM node:18-alpine

# Étape 2: Définir le répertoire de travail dans le conteneur
WORKDIR /usr/src/app

# Étape 3: Copier le fichier package.json et package-lock.json (si présent) dans le conteneur
COPY package*.json ./

# Étape 4: Installer les dépendances définies dans le package.json
RUN npm install

# Étape 5: Copier tout le reste du projet (index.js) dans le conteneur
COPY . .

# Étape 6: Exposer le port sur lequel le serveur Node.js va écouter
EXPOSE 8089

# Étape 7: Lancer le serveur avec Node.js
CMD ["node", "index.js"]