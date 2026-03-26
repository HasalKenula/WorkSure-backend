# Use Java 17 official Eclipse Temurin Alpine image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside container
WORKDIR /app

# Copy your Spring Boot JAR into container
COPY target/*.jar app.jar

# Expose the port your app runs on (update if needed)
EXPOSE 8081

# Run the Spring Boot JAR
ENTRYPOINT ["java","-jar","app.jar"]