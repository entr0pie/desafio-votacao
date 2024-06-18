FROM gradle:8.0-alpine as build

WORKDIR /app

COPY . .

RUN gradle build

FROM amazoncorretto:17-alpine as run

COPY --from=build /app/build/libs/desafio-votacao-0.0.2-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-Dspring.profiles.active=deploy", "-jar", "/app/app.jar" ]