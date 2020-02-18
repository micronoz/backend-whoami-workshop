ARG request_domain
FROM openjdk:latest
ENV request_domain=$request_domain
COPY target/*.jar /myapp.jar
COPY ./keys.yml /keys.yml
CMD ["java", "-jar","myapp.jar"]