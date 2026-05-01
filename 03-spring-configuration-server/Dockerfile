# ---- Build stage --------------------------------------------------------
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

COPY pom.xml .
COPY src ./src

# Download dependencies first (layer cache friendly)
RUN apk add --no-cache maven && \
    mvn dependency:go-offline -q

RUN mvn package -DskipTests -q

# ---- Runtime stage -------------------------------------------------------
FROM eclipse-temurin:21-jre-alpine

# Non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

WORKDIR /app

# Copy the fat jar from builder
COPY --from=builder /build/target/*.jar app.jar

# Local git clone cache; writable by the spring user
RUN mkdir -p /tmp/config-repo && chown spring:spring /tmp/config-repo

USER spring

EXPOSE 8888

# Liveness/readiness check via actuator (wget is in alpine by default)
HEALTHCHECK --interval=30s --timeout=10s --start-period=60s --retries=3 \
    CMD wget --no-verbose --tries=1 --spider http://localhost:8888/actuator/health || exit 1

ENTRYPOINT ["java", \
            "-XX:+UseContainerSupport", \
            "-XX:MaxRAMPercentage=75.0", \
            "-Djava.security.egd=file:/dev/./urandom", \
            "-jar", "app.jar"]
