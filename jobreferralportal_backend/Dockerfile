# Use a base image with Java
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the build files
COPY target/*.jar app.jar

# Add the Oracle Wallet to the container
COPY .src/main/resources/Wallet_ReferralPortalDB /app/Wallet_ReferralPortalDB

# Expose the application port
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "app.jar"]
