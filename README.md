# BackendTeam_D

# CareerFund by Team D

CareerFund is a P2P lending platform to support Learners in Indonesia.

---

## Application Properties Configuration

#### Mandatory (needed for the application to run)

- PostgreSQL

#### Optional (for full functionality)

- AWS S3 Bucket
- Mail Server

## How to start

Type the following command from the root directory of the project to run the application:

- Copy and rename `application-prod.properties` to `application-dev.properties`
- Run:

```
mvn clean install && mvn spring-boot:run
```

## API Documentation

Start the app and go to [http://localhost:8080/v1/swagger-ui.html](http://localhost:8080/v1/swagger-ui.html) to see the documentation.

## SDK

- Java 8-17
- Maven

## Collaborators

- [Muhamad Fahmi Al Kautsar](https://gitlab.com/mfahmialkautsar)
