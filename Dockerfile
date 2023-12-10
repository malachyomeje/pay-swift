

FROM openjdk:17-jdk

WORKDIR /app

COPY target/payswift.jar /app/payswift.jar

EXPOSE 1993

CMD ["java", "-jar", "payswift.jar"]