## Search Service

This is a sample project to demonstrate search service.

###### Commands to build & run this application
```
cd search-service
mvn clean install
sudo docker build -t search-service .
sudo docker run -p 8080:8080 search-service  
```

Once the server is up & running then go to
> http://localhost:8080/search?searchQuery=brown%20fox  
> http://localhost:8080/search?searchQuery=infinity%20and%20beyond

###### Technologies used
```
Java 16, Spring Boot, Lombok, jUnit & Mockito  
Maven, Docker
```