# Из какого репо из Docker.hub берем образ
FROM bellsoft/liberica-openjdk-alpine:17
# Добавляем в контейнер jar файл
ADD /target/PreProject-Task_3.1.4.-0.0.1-SNAPSHOT.jar build.jar
ENTRYPOINT ["java", "-jar", "build.jar"]