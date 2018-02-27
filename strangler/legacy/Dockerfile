FROM maven:3.5-jdk-8-alpine as builder

# Add working directory
WORKDIR /opt/app/src

# Add source code
ADD . .

# Compile application
RUN mvn clean package

# -------------------
FROM openjdk:8-jre-alpine3.7

# Add working directory
WORKDIR /opt/app

# Copy the compiled jar file to the working directory
COPY --from=builder /opt/app/src/target/legacy.jar .

# Expose stand-alone java application
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "legacy.jar"]

