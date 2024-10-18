# Use a minimal JRE base image
FROM eclipse-temurin:18-jre-alpine
# Set the working directory inside the container
WORKDIR /app
# Copy the JAR file into the working directory
COPY ../target/sf-pub-sub-java-client-0.0.1-SNAPSHOT.jar ./sf-pub-sub-java-client-0.0.1.jar
# Expose the application port
EXPOSE 8080
# Run the application with optimized JVM flags for memory and performance
CMD ["java", "-Xmx400m", "-Xms400m", "-XX:TieredStopAtLevel=1", "-noverify", "-jar", "sf-pub-sub-java-client-0.0.1.jar"]




# FROM openjdk:18.0.2.1-slim
# COPY target/sf-pub-sub-java-client-0.0.1-SNAPSHOT.jar .
# CMD java -Xmx400m -Xms400m  -XX:TieredStopAtLevel=1 -noverify -jar sf-pub-sub-java-client-0.0.1-SNAPSHOT.jar
# EXPOSE 8081