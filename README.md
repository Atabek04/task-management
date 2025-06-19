# Task Management API

A REST API for managing tasks, built with Spring Boot and PostgreSQL.

## Features

- Create, read, update, and delete tasks (CRUD operations)
- Task status tracking (PENDING, IN_PROGRESS, COMPLETED)
- Comprehensive validation and error handling
- API documentation with Swagger/OpenAPI
- Containerized with Docker for easy deployment

## Tech Stack

- Java 17
- Spring Boot 3.x
- PostgreSQL
- Docker & Docker Compose
- Swagger/OpenAPI for documentation

## Prerequisites

- Docker and Docker Compose
- JDK 17 (for local development)
- Maven (for local development)

## Running Locally

### 1. Clone the repository:
```bash
https://github.com/Atabek04/task-management.git
cd task-management
```
   
### 2. Configure the `application.yml` and `.env`
   
The application uses the following environment variables:

| Variable | Description | Default |
|----------|-------------|---------|
| APP_PORT | Application port | 8080 |
| DB_HOST | Database hostname | postgres |
| DB_PORT | Database port | 5432 |
| DB_NAME | Database name | taskdb |
| DB_USER | Database username | postgres |
| DB_PASSWORD | Database password | postgres |

### 3. Build the project and run docker containers:
```bash
docker-compose up --build -d
```

### 4. Access the application at http://localhost:8080

## API Documentation

The API is fully documented using Swagger/OpenAPI:

#### Swagger UI: http://localhost:8080/swagger-ui.html

## Testing

### 1. Run tests with:
```bash
mvn test
```

### 2. Run integration tests with:
```bash
mvn verify
```

## License
This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.