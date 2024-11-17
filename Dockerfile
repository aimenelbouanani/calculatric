FROM openjdk:17
COPY build/libs/calculatric-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 6000 001
ENTRYPOINT ["java", "-jar", "app.jar"]
