FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/shop-api-0.0.1-SNAPSHOT.jar /app/shop-api.jar

CMD ["java", "-jar", "shop-api.jar"]