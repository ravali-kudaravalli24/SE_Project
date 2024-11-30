# Stage 1: Build the JAR
FROM maven:3.8-openjdk-17 AS builder

# Set the working directory for Maven
WORKDIR /build

# Copy the pom.xml and dependency information first (to leverage Docker's caching mechanism)
COPY jobreferralportal_backend/pom.xml jobreferralportal_backend/
COPY pom.xml ./

# Download dependencies (cached for faster builds)
RUN mvn -f jobreferralportal_backend/pom.xml dependency:go-offline -B

# Copy the source code into the image
COPY jobreferralportal_backend jobreferralportal_backend/

# Build the application
RUN mvn -f jobreferralportal_backend/pom.xml clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=builder /build/jobreferralportal_backend/target/jobreferralportal-0.0.1-SNAPSHOT.jar app.jar

# Copy the Oracle Wallet files
COPY jobreferralportal_backend/src/main/resources/Wallet_ReferralPortalDB /app/Wallet_ReferralPortalDB

# Expose the application port
EXPOSE 8090

# Run the application
CMD ["java", "-jar", "app.jar"]
