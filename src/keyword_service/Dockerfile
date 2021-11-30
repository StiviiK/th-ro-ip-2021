FROM python:3.9-slim AS build-env
COPY . /app
WORKDIR /app
RUN pip install -r requirements.txt

FROM gcr.io/distroless/python3
COPY --from=build-env /app /app
COPY --from=build-env /usr/local/lib/python3.9/site-packages /usr/local/lib/python3.9/site-packages
ENV PYTHONPATH=/usr/local/lib/python3.9/site-packages
WORKDIR /app
CMD ["app.py"]