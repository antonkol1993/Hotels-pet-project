# =============== СТАДИЯ 1: СБОРКА =================
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# Копируем проект внутрь контейнера
COPY . /app
WORKDIR /app

# Собираем jar без тестов
RUN mvn clean package -DskipTests

# =============== СТАДИЯ 2: ЗАПУСК =================
FROM eclipse-temurin:17-jdk-alpine

# Копируем собранный jar-файл из builder-стадии
COPY --from=builder /app/target/*.jar /app/app.jar

# Копируем application-test.yaml из исходников
COPY src/main/resources/application-test.yaml /config/application-test.yaml

# Запускаем Spring Boot приложение с внешним конфигом
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.config.additional-location=file:/config/application-test.yaml"]
