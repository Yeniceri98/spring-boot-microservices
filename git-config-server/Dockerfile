# Base Image
FROM openjdk:21-jdk-slim

# Copy Jar File
COPY target/git-config-server-0.0.1.jar app.jar

# Port
EXPOSE 8888

# Run Command
ENTRYPOINT ["java","-jar","/app.jar"]