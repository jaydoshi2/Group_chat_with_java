# Start with a base image containing Java runtime
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the project JAR file into the container
COPY target/chat-1.jar /app/Group_Chat.jar

# Expose the port on which your app will run
EXPOSE 8061

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/Group_Chat.jar"]
