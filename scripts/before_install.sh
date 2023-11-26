#!/usr/bin/env bash

REPOSITORY=/opt/achieve/project
#ZIP_FILE=/home/ec2-user/memory_capsule.zip
COMPOSE_FILE=docker-compose-deploy.yml
# Decompress the zip file to the repository directory
#unzip -o $ZIP_FILE -d $REPOSITORY

# Change to the repository directory
cd $REPOSITORY

docker-compose -f $COMPOSE_FILE down -v --rmi "all"
docker-compose -f $ELK_COMPOSE_FILE down -v --rmi "all"
yes | docker system prune -a



