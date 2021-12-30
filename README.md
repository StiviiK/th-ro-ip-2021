# Internet Programmierung 2021

## KeywordService Dev Setup

```bash
cd ./src/keyword_service/
docker image build . -t th-ro-ip-keyword-service:latest
docker run --name keyword-service -d -p 8000:8000 th-ro-ip-keyword-service:latest
```

Set Environment Variable `KEYWORD_SERVICE_URL` to `http://localhost:8000` before running the server.
