FROM maven:3.5-jdk-8
FROM java:8

WORKDIR /app
COPY . .
RUN mvn package

RUN mv /app/target/clothes-backend.jar /app/clothes-backend.jar

RUN rm -r /app/src
RUN rm -r /app/target