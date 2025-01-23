# Étape 1: Utiliser une image de Node.js officielle
FROM node:18-alpine AS build

# Étape 2: Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Étape 3: Copier le fichier package.json et package-lock.json (si présent) dans le conteneur
COPY package*.json ./

RUN npm install

COPY . .


RUN npm run build

FROM node:18-alpine AS production

WORKDIR /app

COPY package*.json .


RUN npm ci --only=production

COPY --from=build /app/dist ./dist

EXPOSE 8089

CMD ["node", "dist/index.js"]

