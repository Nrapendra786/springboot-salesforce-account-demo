# Use a minimal JRE base image
FROM eclipse-temurin:18-jre-alpine
# Set the working directory inside the container
WORKDIR /app
# Copy the JAR file into the working directory
COPY ../target/*.jar ./account-service.jar
# Expose the application port
EXPOSE 8080
# Run the application with optimized JVM flags for memory and performance
CMD ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "account-service.jar"]






# FROM openjdk:18.0.2.1-slim
# COPY target/account-service-0.0.1-SNAPSHOT.jar .
# CMD java -Xmx400m -Xms400m  -XX:TieredStopAtLevel=1 -noverify -jar account-service-0.0.1-SNAPSHOT.jar
# EXPOSE 8080

# # New Docker File
#
# # Step 1: Use Maven to build the application
# FROM maven:3.9.4-eclipse-temurin-17-alpine AS build
#
# # Set the working directory inside the container
# WORKDIR /app
#
# # Install bash (or any other dependencies) if needed
# RUN apk update && apk add bash
#
# # Copy the pom.xml and all project files
# COPY pom.xml .
# COPY src ./src
#
# # Package the application using Maven
# RUN mvn clean package -DskipTests
#
# # Step 2: Use a lightweight OpenJDK image to run the application
# FROM openjdk:17-jdk-alpine
#
# # Set the working directory inside the container
# WORKDIR /app
#
# # Copy the JAR file from the build stage
# COPY --from=build /app/target/*.jar app.jar
#
# # Expose the port that the Spring Boot application will run on
# EXPOSE 8080
#
# # Run the Spring Boot application
# CMD ["java", "-jar", "app.jar"]

