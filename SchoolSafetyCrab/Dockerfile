FROM openjdk:17-alpine
ARG JAR_FILE=/build/libs/SchoolSafetyCrab-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
COPY src/main/resources/application.properties /app/application.properties
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]

