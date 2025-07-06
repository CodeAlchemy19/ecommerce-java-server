# 🔹 Stage 1: Build the Spring Boot app
FROM maven:3.9.6-eclipse-temurin-21 as builder

WORKDIR /app
COPY . .

# Build the jar
RUN mvn clean package -DskipTests

# 🔹 Stage 2: Run the app
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy the jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port
EXPOSE 9000

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
