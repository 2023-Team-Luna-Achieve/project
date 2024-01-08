#!/usr/bin/env bash

# 작업 디렉토리를 /opt/achieve/project 로 변경
REPOSITORY=/opt/achieve/project
cd $REPOSITORY
# 환경변수 DOCKER_APP_NAME을 spring으로 설정
DOCKER_APP_NAME=achieve

# 실행중인 blue가 있는지 확인
# 프로젝트의 실행 중인 컨테이너를 확인하고, 해당 컨테이너가 실행 중인지 여부를 EXIST_BLUE 변수에 저장
#EXIST_BLUE=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps)
EXIST_BLUE=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')
# 테스트 배포 시작한 날짜와 시간을 기록
echo "EXIST_BLUE: 결과  $(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')" >> /opt/deploy.log
echo "배포 시작일자 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

# green이 실행중이면 blue up
# EXIST_BLUE 변수가 비어있는지 확인
if [ -z "$EXIST_BLUE" ]; then

  # 로그 파일(/home/ec2-user/deploy.log)에 "blue up - blue 배포 : port:8081"이라는 내용을 추가
  echo "blue 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

	# docker-compose.blue.yml 파일을 사용하여 spring-blue 프로젝트의 컨테이너를 빌드하고 실행
	sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml up -d --build

  # 120초 동안 대기
  sleep 100

#  BLUE_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | grep Up)
  BLUE_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')
  echo "BLUE_HEALTH: 결과  $(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')" >> /opt/deploy.log
    # blue가 현재 실행중이지 않다면 -> 런타임 에러 또는 다른 이유로 배포가 되지 못한 상태
  if [ -z "$BLUE_HEALTH" ]; then
    sudo echo "에러 발생" >> /opt/error.log
    # blue가 현재 실행되고 있는 경우에만 green을 종료
  else
    sudo docker exec -it frontend-blue tar -czvf /frontend/dist/archive2.tar.gz -C /frontend/dist .
    sudo docker cp frontend-blue:/frontend/dist/archive2.tar.gz /usr/share/nginx/html
    sudo tar -xzvf /usr/share/nginx/html/archive2.tar.gz -C /usr/share/nginx/html

    # /opt/deploy.log: 로그 파일에 "green 중단 시작"이라는 내용을 추가
    echo "green 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

    # docker-compose.green.yml 파일을 사용하여 spring-green 프로젝트의 컨테이너를 중지
    sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml down

     # 사용하지 않는 이미지 삭제
    sudo docker image prune -af

    echo "green 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
  fi

# blue가 실행중이면 green up
else
	echo "green 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
	sudo docker-compose -p ${DOCKER_APP_NAME}-green -f docker-compose-green.yml up -d --build

  sleep 100

  echo "GREEN_HEALTH: 결과  $(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')" >> /opt/deploy.log
  GREEN_HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')

  if [ -z "$GREEN_HEALTH" ]; then
       sudo echo "컴파일 에러 발생" >> /opt/error.log
  else
      sudo docker exec -it frontend-green tar -czvf /frontend/dist/archive2.tar.gz -C /frontend/dist .
      sudo docker cp frontend-green:/frontend/dist/archive2.tar.gz /usr/share/nginx/html
      sudo tar -xzvf /usr/share/nginx/html/archive2.tar.gz -C /usr/share/nginx/html

      # /home/ec2-user/deploy.log: 로그 파일에 "blue 중단 시작"이라는 내용을 추가
      echo "blue 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

      # docker-compose.blue.yml 파일을 사용하여 spring-green 프로젝트의 컨테이너를 중지
      sudo docker-compose -p ${DOCKER_APP_NAME}-blue -f docker-compose-blue.yml down

      # 사용하지 않는 이미지 삭제
      sudo docker image prune -af

      echo "blue 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
  fi
fi
  echo "배포 종료  : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

  echo "    " >> /var/log/nginx/error.log
  sudo systemctl restart nginx
  echo "엔진엑스 리로드 성공인가요" >> /var/log/nginx/error.log
  echo "    " >> /var/log/nginx/error.log

  echo "===================== 배포 완료 =====================" >> /opt/deploy.log
  echo >> /opt/deploy.log
