# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:20

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file into the container
COPY target/first-rest-api.jar /app/first-rest-api.jar

# Expose the port that the application will run on
EXPOSE 8000

# Define the command to run your application
CMD ["java", "-jar", "first-rest-api.jar"]
