# Task Management REST API

A simple REST API for managing tasks built with Java Spring Boot.

## Features

- Create, read, update, and delete tasks
- Mark tasks as completed
- In-memory storage (no database required)
- Input validation for task fields
- Comprehensive unit tests

## Technologies

- Java 17
- Spring Boot 3.1.5
- Maven
- JUnit 5
- Spring Boot Validation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run Tests

```bash
mvn test
```

## API Endpoints

### Create a Task
```bash
POST /api/tasks
Content-Type: application/json

{
  "title": "My Task",
  "description": "Task description"
}
```

### Get All Tasks
```bash
GET /api/tasks
```

### Get a Task by ID
```bash
GET /api/tasks/{id}
```

### Update a Task
```bash
PUT /api/tasks/{id}
Content-Type: application/json

{
  "title": "Updated Task",
  "description": "Updated description"
}
```

### Delete a Task
```bash
DELETE /api/tasks/{id}
```

### Mark Task as Completed
```bash
PATCH /api/tasks/{id}/complete
```

## Task Model

| Field | Type | Description |
|-------|------|-------------|
| id | Long | Auto-generated unique identifier |
| title | String | Task title (required, cannot be empty) |
| description | String | Task description (optional) |
| completed | Boolean | Completion status |
| createdAt | LocalDateTime | Task creation timestamp |
| completedAt | LocalDateTime | Task completion timestamp |

## Validation Rules

- **title**: Cannot be empty or blank (validated with `@NotBlank`)

## Example Usage

Create a task:
```bash
curl -X POST http://localhost:8080/api/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy groceries","description":"Milk, eggs, bread"}'
```

Mark task as completed:
```bash
curl -X PATCH http://localhost:8080/api/tasks/1/complete
```

Get all tasks:
```bash
curl http://localhost:8080/api/tasks
```
