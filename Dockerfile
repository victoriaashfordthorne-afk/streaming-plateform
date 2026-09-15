# ==========================================
# Stage 1: Build the Spring Boot application
# ==========================================
FROM eclipse-temurin:21-jdk AS build

# Set the working directory
WORKDIR /app

# Copy Maven wrapper and pom first
# This helps Docker cache dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give Maven wrapper permission to execute
RUN chmod +x mvnw

# Download dependencies
RUN ./mvnw dependency:go-offline -DskipTests

# Copy the source code
COPY src ./src

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests


# ==========================================
# Stage 2: Run the Spring Boot application
# ==========================================
FROM eclipse-temurin:21-jre

# Set the working directory
WORKDIR /app

# Copy the generated JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

# Render uses the PORT environment variable
EXPOSE 8080

# Start the Spring Boot application
ENTRYPOINT ["sh", "-c", "java -jar app.jar"]