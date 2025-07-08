FROM eclipse-temurin:17-jdk-alpine

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Копируем application-test.yaml из контекста сборки (корня проекта)
COPY src/main/resources/application-test.yaml /config/application-test.yaml
COPY testfile.txt /testfile.txt

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.additional-location=file:/config/application-test.yaml"]

