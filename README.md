# Sporty Backend

A Spring Boot backend application for the Sporty project.

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## Features

- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)

## Development

- H2 Console available at: `http://localhost:8080/h2-console`
- Default database credentials:
  - JDBC URL: `jdbc:h2:mem:sportydb`
  - Username: `sa`
  - Password: (empty)
