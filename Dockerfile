# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package

# Stage 2: Run the application
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/authApp-0.0.4.jar ./authApp.jar
EXPOSE 8080
CMD ["java", "-jar", "authApp.jar"]
