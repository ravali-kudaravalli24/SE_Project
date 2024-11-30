# Stage 1: Build the JAR
FROM maven:3.8-openjdk-17 AS builder

# Set the working directory for Maven
WORKDIR /build

# Copy the pom.xml from the backend module
COPY jobreferralportal_backend/pom.xml ./pom.xml

# Copy the backend source code
COPY jobreferralportal_backend/src ./src

# Download dependencies and build the application
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/jobreferralportal-0.0.1-SNAPSHOT.jar app.jar

# Copy the Oracle Wallet files
COPY jobreferralportal_backend/src/main/resources/Wallet_ReferralPortalDB /app/Wallet_ReferralPortalDB

# Expose the application port
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "app.jar"]
