FROM adoptopenjdk/openjdk16:alpine-jre
COPY target/*.jar search-service.jar
COPY target/test-classes/tmp/* /var/tmp/
EXPOSE 8080
ENTRYPOINT ["java","-jar","/search-service.jar"]