# Use Maven image for the build stage
FROM maven:3.8.6-eclipse-temurin-18-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml ./
COPY src ./src

# Run the Maven build
RUN mvn clean install -DskipTests

# Use a minimal JRE base image for the runtime
FROM eclipse-temurin:18-jre-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/*.jar commons.jar

# Expose the application port
EXPOSE 8089

# Run the application with optimized JVM flags for memory and performance
CMD ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "commons.jar"]

