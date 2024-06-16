# Base image
FROM openjdk:17-jdk

# Create a volume for temporary files
VOLUME /tmp

# Copy the executable JAR file to the Docker image
COPY dl-lab-0.0.1-SNAPSHOT.jar app.jar

# Set the entry point to run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]