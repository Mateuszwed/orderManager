FROM openjdk:17-jdk-alpine
ADD target/orderManager-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -jar orderManager-0.0.1-SNAPSHOT.jar