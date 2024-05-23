#!/usr/bin/env bash
REPOSITORY=/opt/achieve/project
cd $REPOSITORY

DOCKER_APP_NAME=achieve

EXIST_BLUE=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')

echo "배포 시작일자 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log

if [ -z "$EXIST_BLUE" ]; then
  echo "blue 배포 시작 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log

  sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml up -d --build

  sleep 120

  BLUE_STATUS=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')

  BACKEND_CONTAINER_STATUS=sudo docker inspect backend-blue | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'
  FRONTEND_CONTAINER_STATUS=sudo docker inspect frontend-blue | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'
  REDIS_CONTAINER_STATUS=sudo docker inspect redis-blue | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'

  if [ -z "$BLUE_STATUS" ] || [ "$BACKEND_CONTAINER_STATUS" = "exited" ] || [ "$FRONTEND_CONTAINER_STATUS" = "exited" ] || [ "$REDIS_CONTAINER_STATUS" = "exited" ]; then
      echo "에러 발생" >> /opt/deploy.log
      sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-green.yml down -v --rmi all
      echoIfContainerExcited "$BACKEND_CONTAINER_STATUS" "$FRONTEND_CONTAINER_STATUS" "$REDIS_CONTAINER_STATUS" "$BLUE_STATUS"

      cat /opt/deploy-report-email/deploy-fail-email.txt | ssmtp -v -t

  else
      echo "green 중단 시작 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log

      sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml down -v --rmi all

      sudo docker image prune -af # 사용 안하는 이미지

      moveFrontendStaticFile "blue"
      cat /opt/deploy-report-email/deploy-success-email.txt | ssmtp -v -t
      echo "green 중단 완료 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log
  fi

else
      echo "green 배포 시작 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log
      sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml up -d --build
     sleep 120


      GREEN_STATUS=$(sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')
      BACKEND_CONTAINER_STATUS=sudo docker inspect backend-green | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'
      echo "$BACKEND_CONTAINER_STATUS" >>  /opt/deploy.log
      FRONTEND_CONTAINER_STATUS=sudo docker inspect frontend-green | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'
      REDIS_CONTAINER_STATUS=sudo docker inspect redis-green | grep '"Status":' | head -n 1 | awk -F: '{print $2}' | tr -d ' ",'

    if [ -z "$GREEN_STATUS" ] || [ "$BACKEND_CONTAINER_STATUS" = "exited" ] || [ "$FRONTEND_CONTAINER_STATUS" = "exited" ] || [ "$REDIS_CONTAINER_STATUS" = "exited" ]; then
        echo "에러 발생" >> /opt/deploy.log
        sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml down -v --rmi all
        echoIfContainerExcited "$BACKEND_CONTAINER_STATUS" "$FRONTEND_CONTAINER_STATUS" "$REDIS_CONTAINER_STATUS" "$BLUE_STATUS"

        cat /opt/deploy-report-email/deploy-fail-email.txt | ssmtp -v -t


    else
      echo "blue 중단 시작 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log

      sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml down -v --rmi all

      moveFrontendStaticFile "green";

      cat /opt/deploy-report-email/deploy-success-email.txt | ssmtp -v -t

      echo "blue 중단 완료 : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log
    fi
fi
  echo "배포 종료  : $(TZ="Asia/Seoul" date '+%Y-%m-%d %H:%M:%S')" >> /opt/deploy.log

  sudo systemctl reload nginx

  echo "===================== 배포 종료 =====================" >> /opt/deploy.log
  echo >> /opt/deploy.log

