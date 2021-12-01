FROM node:15 as build
WORKDIR /app

COPY package.json .
COPY package-lock.json .
RUN npm install

COPY src src
COPY *.json ./
RUN npm run build:prod

FROM nginx:alpine
RUN apk update --no-cache && \
    apk add jq

COPY --from=build /app/public /usr/share/nginx/html
COPY conf/nginx.conf /etc/nginx/conf.d/default.conf
COPY scripts/00-patch-config.sh /docker-entrypoint.d

EXPOSE 80
