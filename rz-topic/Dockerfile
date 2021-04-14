FROM openjdk:8

MAINTAINER Kim <331599158@qq.com>

ADD target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "/app.jar"]
