# Use Maven image with OpenJDK as the base image
FROM maven:3.8.5-openjdk-11 as build

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY . .

# Build the project using Maven
RUN mvn package

# Use a lightweight OpenJDK image for runtime
FROM openjdk:11-jre-slim

# Set the working directory for the runtime container
WORKDIR /app

# Copy the built JAR file from the Maven build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (default is 8080 for Spring Boot)
EXPOSE 8080

# Set the default command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
