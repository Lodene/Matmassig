# Étape 1 : Utiliser une image Node.js comme base
FROM node:18

# Étape 2 : Définir le répertoire de travail
WORKDIR /app

# Étape 3 : Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Étape 4 : Installer les dépendances, incluant TypeScript
RUN npm install

# Étape 5 : Copier les fichiers du projet
COPY . .

# Étape 6 : Compiler TypeScript en JavaScript
RUN npx tsc

# Étape 7 : Exposer le port pour le WebSocket
EXPOSE 8089

# Étape 8 : Lancer le serveur
CMD ["node", "index.js"]
