# siddhu-jules-repo
This repos is used to create code and do POC using Google Jules Autonomous Coding Agent
# Dog API Consumer

A Spring Boot application that consumes the Dog API and exposes its own REST API.

## APIs Exposed

- `GET /api/breeds`: Returns a list of all dog breeds.
- `GET /api/breeds/{id}`: Returns details of a specific dog breed by its ID.

## How to run

1. Build the project:
   ```bash
   mvn clean install
   ```
2. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## Technologies Used

- Java 21
- Spring Boot 3.3.5
- Maven
- Lombok
