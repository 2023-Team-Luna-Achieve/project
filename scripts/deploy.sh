#!/usr/bin/env bash


function blue_green_deploy() {
  # 로그 파일(/home/ec2-user/deploy.log)에 "blue up - blue 배포 : port:8081"이라는 내용을 추가
  echo "$1 배포 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

	# docker-compose.blue.yml 파일을 사용하여 achieve-blue 프로젝트의 컨테이너를 빌드하고 실행
	sudo docker-compose -p ${DOCKER_APP_NAME}-"$1" -f docker-compose-"$1".yml up -d --build

  # 30초 동안 대기
  sleep 30

  HEALTH=$(sudo docker-compose -p ${DOCKER_APP_NAME}-"$1" -f docker-compose-"$1".yml ps | awk '{$1=""; $2=""; $3=""; $4=""; $5=""; print $0}' | sed 's/^[ \t]*//')
    # blue가 현재 실행중이지 않다면 -> 런타임 에러 또는 다른 이유로 배포가 되지 못한 상태
  if [ -z "$HEALTH" ]; then
    sudo echo "에러 발생 $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/error.log
    # blue가 현재 실행되고 있는 경우에만 green을 종료
  else
    # /opt/deploy.log: 로그 파일에 "green 중단 시작"이라는 내용을 추가
    echo "$2 중단 시작 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log

    # docker-compose.green.yml 파일을 사용하여 achieve-green 프로젝트의 컨테이너를 중지
    sudo docker-compose -p ${DOCKER_APP_NAME}-"$2" -f docker-compose-"$2".yml down -v --rmi all

    sudo docker exec frontend-"$1" tar -czvf /frontend/dist/achieve_static_file.tar.gz -C /frontend/dist .
    sudo docker cp frontend-"$1":/frontend/dist/achieve_static_file.tar.gz /usr/share/nginx/html && echo "achieve_static_file moved successfully!" >> /opt/deploy.log
    sudo tar -xzvf /usr/share/nginx/html/achieve_static_file.tar.gz -C /usr/share/nginx/html && echo "achieve_static_file tar successfully!" >> /opt/deploy.log
    echo "$2 중단 완료 : $(date +%Y)-$(date +%m)-$(date +%d) $(date +%H):$(date +%M):$(date +%S)" >> /opt/deploy.log
  fi
}
