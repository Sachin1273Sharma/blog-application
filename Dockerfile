
FROM maven:3.8.5-openjdk-22 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:22-jdk-slim
WORKDIR /app
COPY --from=build /app/target/blog-application-project-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
