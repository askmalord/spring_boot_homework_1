FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/spring_boot_app1-0.0.1-SNAPSHOT.jar myapp.jar
ENTRYPOINT ["java","-jar","/myapp.jar"]