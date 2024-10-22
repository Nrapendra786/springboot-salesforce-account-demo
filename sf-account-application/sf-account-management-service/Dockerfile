# Stage 1: Build the application
FROM maven:3.8.6-eclipse-temurin-17 as builder

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and source code into the container
COPY pom.xml .
COPY src ./src

# Run Maven to build the package
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-alpine

# Set the working directory for the final image
WORKDIR /app

# Copy the JAR file from the first stage to the final stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]







# # Use a minimal JRE base image
# FROM openjdk:17-jdk-alpine
# # Set the working directory inside the container
# WORKDIR /app
# # Copy the JAR file into the working directory
# COPY /target/*.jar app.jar
# # Expose the application port
# EXPOSE 8080
# # Run the application with optimized JVM flags for memory and performance
# ENTRYPOINT ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "app.jar"]
#CMD ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "app.jar"]








# FROM openjdk:18.0.2.1-slim
# COPY target/account-management-service-0.0.1-SNAPSHOT.jar .
# CMD java -Xmx400m -Xms400m  -XX:TieredStopAtLevel=1 -noverify -jar account-management-service-0.0.1-SNAPSHOT.jar
# EXPOSE 8080