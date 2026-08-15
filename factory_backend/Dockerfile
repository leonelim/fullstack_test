FROM eclipse-temurin:26
COPY . .
ARG JAR_FILE=build/libs/factory-0.0.1-SNAPSHOT.jar
RUN ["./gradlew", "bootJar"]
ENTRYPOINT ["java", "-jar", "build/libs/factory-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
