# Teste de Qualificação Backend O2B

## Technologies
* JAVA 11
* Spring Boot 2.4.x
* Spring Data JPA 2.4.x
* H2
* Lombok 1.18.x
* Maven 3.6.x

## Building and running

* `mvnw clean package`
* `java -jar ./target/o2b-project-0.0.1-SNAPSHOT.jar`

## Running tests only

Needs Postgres and internet

* `mvnw test`

## Order of execution

Using Postman (O2B.postman_collection.json)

* Create a new user
* Authenticate the newly created user
* Use the generated token as Authorization header to be able to CRUD the addresses