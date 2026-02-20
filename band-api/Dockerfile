# Use lightweight JDK 21 image
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy built jar
COPY target/bandapp-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]