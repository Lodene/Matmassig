#!/bin/bash

# Récupérer les services supplémentaires passés en arguments
ADDITIONAL_SERVICES=$@

# Vérifier si des services ont été spécifiés
if [ -z "$ADDITIONAL_SERVICES" ]; then
  echo "Veuillez spécifier les services supplémentaires à lancer. Exemple : ./start-containers.sh orchestrator"
  exit 1
fi

# Services de base toujours nécessaires
BASE_SERVICES="postgres rabbitmq orchestrator authentification-service"

# Combiner les services de base avec les services supplémentaires
SERVICES="$BASE_SERVICES $ADDITIONAL_SERVICES"

# Lancer uniquement les services spécifiés
echo "Démarrage des services : $SERVICES"
docker compose up -d --build $SERVICES
