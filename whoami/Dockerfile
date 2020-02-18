FROM openjdk:latest
COPY target/*.jar /myapp.jar
COPY /tmp/keys.yml /keys.yml
CMD ["java", "-jar","myapp.jar"]