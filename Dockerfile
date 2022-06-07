FROM openjdk:16-jdk-slim
EXPOSE 8080
ENV TZ=Asia/Seoul
COPY ./application-infrastructure/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]