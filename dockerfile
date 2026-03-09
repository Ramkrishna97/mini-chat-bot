# Dockerfile (optional but recommended)
FROM openjdk:17-jdk-slim
COPY target/*.jar app.jar
EXPOSE 8008
ENTRYPOINT ["java", "-jar", "/app.jar"]