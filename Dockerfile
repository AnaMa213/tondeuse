FROM openjdk:17

# Set the working directory to /app
WORKDIR /app

# Copy the fat jar into the container at /app
COPY ./tondeuse-job/target/*.jar /app/app.jar

# Make port 8080 available to the world outside this container
EXPOSE 8081

# Run jar file when the container launches
CMD ["java", "-jar", "app.jar"]