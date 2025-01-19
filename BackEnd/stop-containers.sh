#!/bin/bash

# Récupérer les services supplémentaires passés en arguments
ADDITIONAL_SERVICES=$@

# Vérifier si des services ont été spécifiés
if [ -z "$ADDITIONAL_SERVICES" ]; then
  echo "Veuillez spécifier les services supplémentaires à arrêter. Exemple : ./stop-containers.sh orchestrator"
  exit 1
fi

# Services de base toujours nécessaires
BASE_SERVICES="postgres rabbitmq"

# Combiner les services de base avec les services supplémentaires
SERVICES="$BASE_SERVICES $ADDITIONAL_SERVICES"

# Arrêter uniquement les services spécifiés
echo "Arrêt des services : $SERVICES"
docker compose stop $SERVICES
docker compose rm -f $SERVICES
