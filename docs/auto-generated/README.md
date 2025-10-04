```markdown
# Product Inventory API

A Spring Boot reactive web application for managing product inventory. This API allows for the creation, retrieval, updating, and deletion of product information using a reactive programming model.

## Architecture

```mermaid
sequenceDiagram
    participant User/Client
    participant ProductController
    participant CreateProduct
    participant Product
    participant InMemoryProductRepository
    participant In-Memory Data

    User/Client->>ProductController: POST /api/v1/products\nProductRequest (JSON)
    activate ProductController
    ProductController->>ProductController: Validate ProductRequest
    alt Validation Failed
        ProductController-->>User/Client: HTTP 400 - Bad Request
        deactivate ProductController
    else Validation Passed
        ProductController->>Product: Build Product from ProductRequest
        activate Product
        Product-->>ProductController: Product (Domain Model)
        deactivate Product
        ProductController->>CreateProduct: execute(Product)
        activate CreateProduct
        CreateProduct->>InMemoryProductRepository: save(Product)
        activate InMemoryProductRepository
        InMemoryProductRepository->>In-Memory Data: Store Product
        activate In-Memory Data
        In-Memory Data-->>InMemoryProductRepository: Acknowledge
        deactivate In-Memory Data
        InMemoryProductRepository-->>CreateProduct: Mono<Product>
        deactivate InMemoryProductRepository
        CreateProduct-->>ProductController: Mono<Product>
        deactivate CreateProduct
        ProductController-->>User/Client: HTTP 201 - Created\nProduct (JSON)
        deactivate ProductController
    end
```

## Key Features

*   **Reactive API:** Built with Spring WebFlux for asynchronous and non-blocking operations.
*   **CRUD Operations:** Supports creation, retrieval, updating, and deletion of products.
*   **Validation:** Uses bean validation for request objects.
*   **OpenAPI Documentation:** Automatically generates OpenAPI 3 documentation and Swagger UI.
*   **In-Memory Data Storage:** Uses an in-memory `ConcurrentHashMap` for data persistence.
*   **Containerized Deployment:** Includes a `Dockerfile` and `docker-compose.yml` for easy deployment.

## Quick Start

1.  **Clone the repository.**
2.  **Build the application using Maven:**

    ```bash
    mvn clean install
    ```

3.  **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

## Documentation

*   [Technology Stack](01-technology-stack.md)
*   [Core Features](02-core-features.md)
*   [API Endpoints](03-api-endpoints.md)

## API Usage Examples

**Base URL:** `/api/v1/products`

*   **Get All Products:**

    ```bash
    curl -X GET http://localhost:8080/api/v1/products
    ```

*   **Get Product by ID:**

    ```bash
    curl -X GET http://localhost:8080/api/v1/products/654321a77b76613c69a6210e
    ```

*   **Create a New Product:**

    ```bash
    curl -X POST \
      http://localhost:8080/api/v1/products \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "Noise-Cancelling Headphones",
        "description": "Premium over-ear headphones with active noise cancellation",
        "price": 299.99,
        "quantity": 20
      }'
    ```

*   **Update Product:**

    ```bash
    curl -X PUT \
      http://localhost:8080/api/v1/products/654321a77b76613c69a6210e \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "Updated Noise-Cancelling Headphones",
        "description": "Premium over-ear headphones with active noise cancellation and improved comfort",
        "price": 349.99,
        "quantity": 25
      }'
    ```

*   **Patch Product:**

    ```bash
    curl -X PATCH \
      http://localhost:8080/api/v1/products/654321a77b76613c69a6210e \
      -H 'Content-Type: application/json' \
      -d '{
        "price": 329.99
      }'
    ```

*   **Delete Product:**

    ```bash
    curl -X DELETE http://localhost:8080/api/v1/products/654321a77b76613c69a6210e
    ```

## Configuration Properties

The following properties can be configured in `application.properties`:

*   `spring.application.name=product-api`
*   `springdoc.api-docs.path=/api-docs`
*   `springdoc.swagger-ui.path=/`
*   `springdoc.swagger-ui.operationsSorter=method`
*   `springdoc.swagger-ui.tagsSorter=alpha`

## Docker Deployment

1.  **Build the Docker image:**

    ```bash
    docker build -t product-api .
    ```

2.  **Run the application using Docker Compose:**

    ```bash
    docker-compose up
    ```

The application will be accessible at `http://localhost:8080`. The Swagger UI will be available at `http://localhost:8080/swagger-ui.html`.
```