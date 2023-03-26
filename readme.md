#
또개질입니다.


### Tech
- aws ec2(ubuntu 20.04), rds, route 53, alb, certification manager, cloud logging (monitoring)
- spring boot 3.0 + kotlin

### EC2 settings

1. 사용자 데이터 작성
```
#!/bin/bash
sudo apt-get update -y
sudo apt-get remove docker docker-engine docker.io -y
sudo apt-get install docker.io -y
sudo service docker start
sudo chmod 666 /var/run/docker.sock
sudo usermod -a -G docker ubuntu
```

2. logging agent 설정
```
sudo mkdir /etc/google
sudo mkdir /etc/google/auth
sudo vi /etc/google/auth/application_default_credentials.json

# serviceaccount key 입력

GOOGLE_APPLICATION_CREDENTIALS="/etc/google/auth/application_default_credentials.json"
sudo chown root:root "$GOOGLE_APPLICATION_CREDENTIALS"
sudo chmod 0400 "$GOOGLE_APPLICATION_CREDENTIALS"

curl -sSO https://dl.google.com/cloudagents/add-logging-agent-repo.sh
sudo bash add-logging-agent-repo.sh --also-install

sudo service google-fluentd status

ps -ef | grep google
```

3. elastic ip 에 ec2 연결
4. alb target group 에 80 포트로 ec2 설정
