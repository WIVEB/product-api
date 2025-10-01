# Product Inventory API

A reactive REST API built with Spring Boot 3.5.6 and WebFlux for managing product inventory. This project follows Clean Architecture principles and includes comprehensive testing with 100% code coverage.

## Features

- **Full CRUD Operations**: GET, POST, PUT, PATCH, and DELETE for products
- **Clean Architecture**: Separation of domain, use cases, infrastructure, and presentation layers
- **Reactive Programming**: Built with Spring WebFlux and Project Reactor
- **In-Memory Storage**: Pre-loaded with 10 default products
- **100% Test Coverage**: Comprehensive unit and integration tests
- **Docker Support**: Dockerfile and docker-compose for easy deployment
- **CI/CD Pipeline**: GitHub Actions workflow for automated testing and building

## Technology Stack

- **Java 21**
- **Spring Boot 3.5.6**
- **Spring WebFlux** (Reactive web framework)
- **Project Reactor** (Reactive programming)
- **Springdoc OpenAPI** (Swagger documentation)
- **Lombok** (Reduce boilerplate)
- **JaCoCo** (Code coverage)
- **JUnit 5** & **Mockito** (Testing)

## Architecture

The project follows Clean Architecture with the following layers:

```
src/main/java/com/example/product/api/produc/
├── domain/
│   ├── model/Product.java
│   └── repository/ProductRepository.java
├── usecase/
│   ├── GetAllProducts.java
│   ├── GetProductById.java
│   ├── CreateProduct.java
│   ├── UpdateProduct.java
│   ├── PatchProduct.java
│   └── DeleteProduct.java
├── infrastructure/
│   └── repository/InMemoryProductRepository.java
└── presentation/
    ├── controller/ProductController.java
    └── dto/
        ├── ProductRequest.java
        └── ProductPatchRequest.java
```

## API Endpoints

### Get All Products
```bash
GET /api/v1/products
```

### Get Product by ID
```bash
GET /api/v1/products/{id}
```

### Create Product
```bash
POST /api/v1/products
Content-Type: application/json

{
  "name": "Product Name",
  "description": "Product Description",
  "price": 99.99,
  "quantity": 10
}
```

### Update Product (Full)
```bash
PUT /api/v1/products/{id}
Content-Type: application/json

{
  "name": "Updated Name",
  "description": "Updated Description",
  "price": 149.99,
  "quantity": 20
}
```

### Update Product (Partial)
```bash
PATCH /api/v1/products/{id}
Content-Type: application/json

{
  "price": 149.99
}
```

### Delete Product
```bash
DELETE /api/v1/products/{id}
```

## API Documentation

This API includes **Swagger/OpenAPI** documentation for easy exploration and testing.

### Access Swagger UI

Once the application is running, visit:
- **Swagger UI**: http://localhost:8080/
- **OpenAPI JSON**: http://localhost:8080/api-docs

The Swagger UI provides:
- Interactive API documentation
- Try-it-out functionality for all endpoints
- Request/response examples
- Schema documentation

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- Docker (optional)

### Build and Run

#### Using Maven

```bash
# Build the project
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

The application will start on `http://localhost:8080`

#### Using Docker

```bash
# Build and run with Docker Compose
docker-compose up --build
```

#### Using Docker directly

```bash
# Build the image
docker build -t product-api .

# Run the container
docker run -p 8080:8080 product-api
```

## Testing

### Run All Tests
```bash
./mvnw test
```

### Run Specific Test Class
```bash
./mvnw test -Dtest=ProductControllerTest
```

### Generate Coverage Report
```bash
./mvnw test jacoco:report
```

View the coverage report at: `target/site/jacoco/index.html`

## Default Products

The application is pre-loaded with 10 products:

1. Laptop - $1,299.99
2. Smartphone - $899.99
3. Wireless Headphones - $249.99
4. 4K Monitor - $449.99
5. Mechanical Keyboard - $129.99
6. Wireless Mouse - $49.99
7. Tablet - $599.99
8. HD Webcam - $89.99
9. Bluetooth Speaker - $79.99
10. USB-C Charger - $39.99

## CI/CD

The project includes a GitHub Actions workflow that:

- Runs on push/pull requests to master/main branch
- Builds the project with Maven
- Runs all tests
- Generates and uploads coverage reports
- Builds Docker image
- Tests the Docker image

## Project Structure

```
product-api/
├── .github/
│   └── workflows/
│       └── ci.yml
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
│       └── java/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

## License

This project is licensed under the MIT License.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
