FROM openjdk:8-jdk-alpine
LABEL maintainer="victor.fsouza2@gmail.com"
VOLUME /tmp
EXPOSE 9000
ARG JAR_FILE=target/app-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]