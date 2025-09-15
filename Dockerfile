# ---- Build stage ----
FROM gradle:8.8-jdk21 AS build
WORKDIR /app

# Copy Gradle files first (better caching)
COPY build.gradle settings.gradle gradlew gradle /app/
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Download dependencies
RUN ./gradlew build --no-daemon || return 0

# Copy project source
COPY src /app/src

# Build the JAR
RUN ./gradlew bootJar --no-daemon

# ---- Runtime stage ----
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
