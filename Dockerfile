# Из какого репо из Docker.hub берем образ
FROM debian:10-slim as glibc-base
# Добавляем в контейнер jar файл
ADD /tagret/PreProject-Task_3.1.4.jar build.jar
ENTRYPOINT ["java", "-jar", "build.jar"]
