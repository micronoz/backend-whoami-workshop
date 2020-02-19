FROM openjdk:latest
COPY target/backend-1.0.0.jar /myapp.jar
COPY ./keys.yml /keys.yml
CMD ["java", "-jar","myapp.jar"]
