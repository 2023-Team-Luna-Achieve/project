#!/usr/bin/env bash

REPOSITORY=/opt/achieve/project
COMPOSE_FILE=docker-compose-prod.yml


# Change to the repository directory
cd $REPOSITORY

docker-compose -f $COMPOSE_FILE down -v --rmi "all"
docker-compose -f $ELK_COMPOSE_FILE down -v --rmi "all"
yes | docker system prune -a