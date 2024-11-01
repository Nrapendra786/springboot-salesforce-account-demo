# Start with a Maven image to build the project
FROM maven:3.8.6-eclipse-temurin-18 AS build

# Set working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml ./
COPY src ./src

# Build the project and create a JAR file
RUN mvn clean install -DskipTests

# Use a lightweight Java runtime for the final image
FROM eclipse-temurin:18-jre-alpine

# Set working directory in the new image
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar account-management-service-0.0.1.jar

# Expose port (adjust if needed)
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "account-management-service-0.0.1.jar"]




# Use Maven image for the build stage
#FROM maven:3.8.6-eclipse-temurin-18-alpine AS build
# Set the working directory inside the container
#WORKDIR /app
# Copy the Maven project files
#COPY pom.xml ./
#COPY src ./src
# Run the Maven build
#RUN mvn clean package -DskipTests
# Use a minimal JRE base image for the runtime
#FROM eclipse-temurin:18-jre-alpine
# Set the working directory inside the container
# WORKDIR /app
# Copy the JAR file from the build stage
#COPY --from=build /app/target/*.jar account-management-service.jar
# Expose the application port
#EXPOSE 8082
# Run the application with optimized JVM flags for memory and performance
# CMD ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "account-management-service.jar"]

