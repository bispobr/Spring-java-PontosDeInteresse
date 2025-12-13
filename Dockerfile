FROM openjdk:21-ea-1-jdk-slim

WORKDIR /app

COPY target/gps-0.0.1-SNAPSHOT.jar /app/gps.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/gps.jar"]


