FROM openjdk:17-oracle
LABEL authors="Yaroslav"
EXPOSE 8080

COPY target/SpringBootPractice-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]

