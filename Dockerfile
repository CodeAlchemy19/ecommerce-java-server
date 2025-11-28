# ----------- STAGE 1: BUILD JAR -----------
FROM eclipse-temurin:21-jdk AS builder

WORKDIR /app

# Copy Maven files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (cache)
RUN ./mvnw dependency:go-offline

# Copy source code
COPY src src

# Build
RUN ./mvnw clean package -DskipTests


# ----------- STAGE 2: RUN APP -----------
FROM eclipse-temurin:21-jdk

WORKDIR /app

# Copy jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
