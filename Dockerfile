# Use a base image with Java installed
FROM openjdk:17-jdk-slim

# Add a volume pointing to /tmp
VOLUME /tmp

# Expose the port your application runs on
EXPOSE 8080

# Add the application JAR file
ARG JAR_FILE=target/emerson-care-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]