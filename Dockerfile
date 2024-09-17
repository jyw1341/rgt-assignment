FROM eclipse-temurin:17.0.12_7-jdk as build
WORKDIR /app
COPY . /app/
RUN ./gradlew clean bootJar

FROM eclipse-temurin:17.0.11_9-jre
WORKDIR /app
COPY --from=build /app/api/build/libs/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

