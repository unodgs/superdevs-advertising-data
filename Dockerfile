FROM openjdk:14-alpine
COPY build/libs/advertising-data-*-all.jar advertising-data.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "advertising-data.jar"]