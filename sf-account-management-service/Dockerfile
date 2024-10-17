FROM openjdk:18.0.2.1-slim
COPY target/account-management-service-1.0-SNAPSHOT.jar .
CMD java -Xmx400m -Xms400m  -XX:TieredStopAtLevel=1 -noverify -jar account-management-service-1.0-SNAPSHOT.jar
EXPOSE 8080