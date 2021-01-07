# genius-discography

Get songs of artists using Genius APIs

The Application can be started by:
1. Building the spring boot service
```
mvn clean install
```
2. Either by building and running the docker as explained below, or locally by the following command
```
mvn spring-boot:run
```

### Tech Stack Used
1. Java 11
2. Maven
3. JUnit
4. Docker
5. Open API

### API specifications
API first approach has been followed. API-specifications have been added in accordance with the Open API specs 3.0.3. 
More about them can be found [here](docs/api-spec.yaml).

A sample call, for reference
```
curl -L -X GET 'https://api.genius.com/search?q=Marshmello&exact_match=true' \
-H 'Authorization: Bearer <Valid Token>'
```

### Rest Best Practices
Restful APIs have been designed in a way that they are reusable, generic and easily extendable.
Paging and Sorting is supported by the API, by forwarded the params to the Genius API.


### Actuator
Actuator endpoint has been exposed [here](http://localhost:8080/actuator/health) to track the application health.


### Containerization
Containerization has been done using [docker](Dockerfile).

For Reference, the following commands can be used after building the code(jar) to build and run docker images.
```
docker build --tag genius-discography .
```
```
docker run --publish 8080:8080 genius-discography
```
### Test Driven
TDD approach has been used and test cases have been added to ensure the application works as expected.

### Assumptions
1. Genius API calls are made with timeouts of 10 seconds.
2. API token is directly forwarded to Genius calls for validation.
3. If no Artist found, the API responds with search failure.
 