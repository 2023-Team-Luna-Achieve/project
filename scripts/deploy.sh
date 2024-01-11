#!/usr/bin/env bash

# 작업 디렉토리를 /opt/achieve/project 로 변경
REPOSITORY=/opt/achieve/project
cd $REPOSITORY

DOCKER_APP_NAME=achieve

# 실행중인 blue가 있는지 확인
EXIST_BLUE=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')

# 테스트 배포 시작한 날짜와 시간을 기록
echo "배포 시작일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

# green이 실행중이면 blue up
# EXIST_BLUE 변수가 비어있는지 확인
if [ -z "$EXIST_BLUE" ]; then

  # 로그 파일(/home/ec2-user/deploy.log)에 "blue up - blue 배포 : port:8081"이라는 내용을 추가
  echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

	# docker-compose.blue.yml 파일을 사용하여 achieve-blue 프로젝트의 컨테이너를 빌드하고 실행
	sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml up -d --build

  # 30초 동안 대기
  sleep 30

  BLUE_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')
  echo "EXIST_BLUE: 결과 $(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')" >> /opt/deploy.log
    # blue가 현재 실행중이지 않다면 -> 런타임 에러 또는 다른 이유로 배포가 되지 못한 상태
  if [ -z "$BLUE_HEALTH" ]; then
    sudo echo "에러 발생 $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/error.log
    # blue가 현재 실행되고 있는 경우에만 green을 종료
  else
    # /opt/deploy.log: 로그 파일에 "green 중단 시작"이라는 내용을 추가
    echo "green 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

    # docker-compose.green.yml 파일을 사용하여 achieve-green 프로젝트의 컨테이너를 중지
    sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml down -v --rmi all

     # 사용하지 않는 이미지 삭제
    sudo docker image prune -af

      sudo docker exec frontend-blue tar -czvf /frontend/dist/achieve_static_file.tar.gz -C /frontend/dist .
      sudo docker cp frontend-blue:/frontend/dist/achieve_static_file.tar.gz /usr/share/nginx/html && echo "achieve_static_file moved successfully!" >> /opt/deploy.log
      sudo tar -xzvf /usr/share/nginx/html/achieve_static_file.tar.gz -C /usr/share/nginx/html && echo "achieve_static_file tar successfully!" >> /opt/deploy.log
    echo "green 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
  fi

# blue가 실행중이면 green up
else
	echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
	sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml up -d --build

  sleep 30

  GREEN_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')

  if [ -z "$GREEN_HEALTH" ]; then
       sudo echo "에러 발생 $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/error.log
  else
      # /home/ec2-user/deploy.log: 로그 파일에 "blue 중단 시작"이라는 내용을 추가
      echo "blue 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

      # docker-compose.blue.yml 파일을 사용하여 achieve-green 프로젝트의 컨테이너를 중지, 볼륨, 이미지 삭제
      sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml down -v --rmi all

      sudo docker exec frontend-green tar -czvf /frontend/dist/achieve_static_file.tar.gz -C /frontend/dist .
      sudo docker cp frontend-green:/frontend/dist/achieve_static_file.tar.gz /usr/share/nginx/html && echo "achieve_static_file moved successfully!" >> /opt/deploy.log
      sudo tar -xzvf /usr/share/nginx/html/achieve_static_file.tar.gz -C /usr/share/nginx/html && echo "achieve_static_file tar successfully!" >> /opt/deploy.log

      echo "blue 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
  fi
fi
  echo "배포 종료  : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

  sudo systemctl restart nginx

  echo "===================== 배포 완료 =====================" >> /opt/deploy.log
  echo >> /opt/deploy.log
