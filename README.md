# A sample ToDo Spring Boot web application

This project is a simple web application written in Java powered by Spring Boot. Its main purpose is to demonstrate how to build RESTful Web Service with Spring.

The project is used the following technologies:

* Java 11
* Spring Boot
* Database MongoDB
* Maven

 * The architecture of the application is built with the following components:
   * com.example.domain.dto: Objects which are used for outside communication via the API
   * com.example.controller: Implements the processing logic of the application, parsing of parameters and validation of in- and outputs.
   * com.example.service: Implements the business logic.
   * com.example.repository: Interface for the database. Inserts, updates, deletes and reads objects from the database.
   * com.example.domain.dao: Functional Objects which might be persisted in the database.

# Running Spring TODO App locally

STEP 1 - Checkout Spring TODO app
```
git clone https://github.com/sergeevyi/spring-boot-rest-todo.git
cd spring-boot-rest-todo
```

STEP 2 - Run Spring TODO App locally
```
mvn package spring-boot:run
```

# Using the application
Once the application has started, you can use the application via the Swagger UI.

```
http://localhost:8081/swagger-ui.html
```
