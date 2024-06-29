FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN chmod +x ./gradlew && ./gradlew build

FROM openjdk:17.0.1-jdk-slim
COPY --from=build build/libs/demo-0.0.1-SNAPSHOT.jar Cricket-Api.jar
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "Cricket-Api.jar"]