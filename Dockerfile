
FROM amazoncorretto:11-alpine-jdk
EXPOSE 8080
MAINTAINER ua.pt
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
