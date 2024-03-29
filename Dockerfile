FROM openjdk:16-jdk-slim
EXPOSE 8080

ENV TZ=Asia/Seoul

ARG PROFILE
ENV PROFILE ${PROFILE}
ARG CLOUD_CONFIG_USERNAME
ENV CLOUD_CONFIG_USERNAME ${CLOUD_CONFIG_USERNAME}
ARG CLOUD_CONFIG_PASSWORD
ENV CLOUD_CONFIG_PASSWORD ${CLOUD_CONFIG_PASSWORD}
ARG CLOUD_CONFIG_IMPORT_URL
ENV CLOUD_CONFIG_IMPORT_URL ${CLOUD_CONFIG_IMPORT_URL}

COPY ./application-infrastructure/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
