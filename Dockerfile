# Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim

# Add Maintainer Info
LABEL maintainer="palmerodev@gmail.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE

# Environment Variables ARGS
ARG DATABASE_URL
ARG DATABASE_USER
ARG DATABASE_PASS
ARG SERVER_BASE_URL

# Environment Variables
ENV DATABASE_URL=$DATABASE_URL
ENV DATABASE_USER=$DATABASE_USER
ENV DATABASE_PASS=$DATABASE_PASS
ENV SERVER_URL=$SERVER_BASE_URL

# Add the application's jar to the container
ADD ${JAR_FILE} app.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]