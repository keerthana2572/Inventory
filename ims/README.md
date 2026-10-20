# Inventory Management System (IMS)

This is a Spring Boot application for managing inventory with dynamic pricing capabilities.

## Features

- Product management (CRUD operations)
- Inventory tracking
- Dynamic pricing suggestions
- Reorder recommendations
- Agentic recommendation loop
- RESTful API

## Technologies Used

- Spring Boot 3.x
- Java 17+
- H2 Database
- JPA/Hibernate
- Maven

## API Endpoints

### Products

- `POST /api/products` - Create a new product
- `GET /api/products` - Get all products (with optional filtering by status and category)
- `PATCH /api/products/{id}/stock` - Update product stock level
- `POST /api/products/{id}/orders` - Simulate an order
- `POST /api/products/{id}/suggest-pricing` - Generate pricing suggestion
- `POST /api/products/{id}/suggest-pricing/stream` - Stream pricing suggestion (SSE)
- `POST /api/products/{id}/suggest-reorder` - Generate reorder suggestion

### Pricing Suggestions

- `PATCH /api/pricing-suggestions/{id}` - Update pricing suggestion status (accept/reject)

### Reorder Suggestions

- `PATCH /api/reorder-suggestions/{id}` - Update reorder suggestion status (accept/reject)

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Installation

1. Clone the repository
2. Navigate to the project directory
3. Run `mvn clean install`
4. Run `mvn spring-boot:run`

### Accessing the Application

- API: http://localhost:8080/api/
- H2 Console: http://localhost:8080/h2-console

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/inventory/ims/
│   │       ├── config/          # Configuration classes
│   │       ├── controller/      # REST controllers
│   │       ├── dto/             # Data Transfer Objects
│   │       ├── exception/       # Custom exceptions
│   │       ├── mapper/          # Entity-DTO mappers
│   │       ├── model/           # JPA entities
│   │       ├── repository/      # Spring Data repositories
│   │       ├── strategy/        # Business strategy implementations
│   │       └── ImsApplication.java
│   └── resources/
│       ├── application.properties
│       └── ...
└── test/
```

## Database Schema

The application uses an H2 in-memory database for development purposes. The schema is automatically created on startup.

## CORS Configuration

CORS is configured to allow requests from:
- http://localhost:5173 (Vite/React default)
- http://localhost:4200 (Angular default)

## License

This project is licensed under the MIT License.