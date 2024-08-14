FROM gradle:7.4.2-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle build --no-daemon

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/calinify-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080