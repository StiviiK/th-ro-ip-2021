# Internet Programmierung 2021

## KeywordService Dev Setup
``` bash
cd ./src/keyword_service/
docker image build . -t th-ro-ip-keyword-service:latest
docker run --name keyword-service -d -p 5002:5002 th-ro-ip-keyword-service:latest