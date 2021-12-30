FROM gradle:7.2.0-jdk16 as builder
WORKDIR /home/gradle/src

COPY --chown=gradle:gradle . .
RUN gradle clean build -i --stacktrace -x test

WORKDIR /home/gradle/src/output
ARG JAR_NAME=/home/gradle/src/build/libs/backend-0.0.1-SNAPSHOT.jar
RUN java -Djarmode=layertools -jar ${JAR_NAME} extract

FROM amazoncorretto:16-alpine
WORKDIR /home/spring/application

RUN addgroup -S spring \
    && adduser -S spring -G spring

RUN apk update \
    && apk add ca-certificates \
    && rm -rf /var/cache/apk/* \
    && update-ca-certificates

ARG SOURCES_DIR=/home/gradle/src/output/
COPY --from=builder --chown=spring:spring ${SOURCES_DIR}/dependencies/ ./
COPY --from=builder --chown=spring:spring ${SOURCES_DIR}/spring-boot-loader/ ./
COPY --from=builder --chown=spring:spring ${SOURCES_DIR}/snapshot-dependencies/ ./
COPY --from=builder --chown=spring:spring ${SOURCES_DIR}/application/ ./

USER spring:spring
CMD ["java", "org.springframework.boot.loader.JarLauncher"]
