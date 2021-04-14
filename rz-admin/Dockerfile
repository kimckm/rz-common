FROM openjdk:8

MAINTAINER Kim <331599158@qq.com>

ADD target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "-Deureka.client.serviceUrl.defaultZone=http://eureka:8761/eureka/", "/app.jar"]
