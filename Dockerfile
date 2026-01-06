FROM maven:3.9.6-eclipse-temurin-17 AS builder

COPY . .

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy

EXPOSE 8080

COPY --from=builder /target/todolist-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]