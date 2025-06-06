# Use an official OpenJDK image as the base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
COPY target/tracking-service-0.0.1-SNAPSHOT.jar /app.jar

# Expose the port your app is running on (default is 8080 for Spring Boot)
EXPOSE 8080

# Run the Spring Boot application when the container starts
ENTRYPOINT ["java", "-jar", "/app.jar"]