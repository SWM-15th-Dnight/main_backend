FROM openjdk:17

WORKDIR /app

COPY build/libs/calinify-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080