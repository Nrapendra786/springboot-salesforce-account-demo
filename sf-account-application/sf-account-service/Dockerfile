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


