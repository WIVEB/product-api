# Product Inventory API Documentation

This document describes the core features of the Product Inventory API, a Spring Boot reactive web application.

## 1. Overview of Application Workflow

The application provides a REST API for managing products.  Requests are handled by use case classes, which orchestrate interactions with the `ProductRepository`.  The `ProductRepository` provides data access operations, currently implemented with an in-memory store.  The application leverages Spring's dependency injection to wire components together.  The application is built using a reactive programming model with `reactor-core`.

## 2. Business Logic Components

The application follows a Clean Architecture approach, separating concerns into distinct layers.

### 2.1 Domain Layer

#### 2.1.1 `Product` (Domain Model)

Represents a product in the inventory.

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product entity representing an item in the inventory")
public class Product {
    @Schema(description = "Unique identifier of the product", example = "1")
    private String id;

    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Schema(description = "Detailed description of the product", example = "High-performance laptop for professional use")
    private String description;

    @Schema(description = "Price of the product in USD", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "15")
    private Integer quantity;
}
```

Attributes:
*   `id`: Unique identifier (String).
*   `name`: Product name (String).
*   `description`: Product description (String).
*   `price`: Product price (BigDecimal).
*   `quantity`: Available quantity (Integer).

#### 2.1.2 `ProductRepository` (Repository Interface)

Defines the contract for data access operations on `Product` entities.

```java
public interface ProductRepository {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Product> update(String id, Product product);
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}
```

Methods:

*   `findAll()`: Returns a `Flux` of all products.
*   `findById(String id)`: Returns a `Mono` of a product with the given ID.
*   `save(Product product)`: Saves a product and returns a `Mono` of the saved product.
*   `update(String id, Product product)`: Updates a product with the given ID and returns a `Mono` of the updated product.
*   `deleteById(String id)`: Deletes a product with the given ID and returns a `Mono<Void>`.
*   `existsById(String id)`: Returns a `Mono<Boolean>` indicating whether a product with the given ID exists.

### 2.2 Use Case Layer

The Use Case layer contains classes that implement specific business operations.  Each use case class uses the `ProductRepository` to interact with the data layer. All use cases are implemented as Spring `@Service` components.

*   **`GetAllProducts`**: Retrieves all products.  Uses `ProductRepository.findAll()`.
*   **`GetProductById`**: Retrieves a product by ID. Uses `ProductRepository.findById(id)`.
*   **`CreateProduct`**: Creates a new product.  Assigns a UUID to the `id` field and uses `ProductRepository.save(product)`.
*   **`UpdateProduct`**: Updates an existing product. Uses `ProductRepository.update(id, product)`.
*   **`PatchProduct`**: Partially updates an existing product.  Retrieves the existing product using `ProductRepository.findById(id)`, applies the non-null values from the provided `Product` object, and then uses `ProductRepository.update(id, existingProduct)` to persist the changes.
*   **`DeleteProduct`**: Deletes a product. Uses `ProductRepository.deleteById(id)`.

Example: `CreateProduct`

```java
@Service
@RequiredArgsConstructor
public class CreateProduct {
    private final ProductRepository productRepository;

    public Mono<Product> execute(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }
}
```

### 2.3 Infrastructure Layer

#### 2.3.1 `InMemoryProductRepository`

An implementation of the `ProductRepository` interface that stores product data in memory using a `ConcurrentHashMap`. It is annotated with `@Repository`, making it a Spring-managed component.

The repository is initialized with a set of default products using a `@PostConstruct` method.

The methods implement the repository interface using the in-memory `products` map.

## 3. Dependency Injection

Spring's dependency injection is used extensively.  Use case classes are annotated with `@Service` and the `ProductRepository` is injected into the use case classes using constructor injection (`@RequiredArgsConstructor` from Lombok).  This allows Spring to manage the dependencies between components.

Example:

```java
@Service
@RequiredArgsConstructor
public class GetAllProducts {
    private final ProductRepository productRepository;

    public Flux<Product> execute() {
        return productRepository.findAll();
    }
}
```

## 4. Domain Models, Use Cases, and Repository Pattern

*   **Domain Model**: `Product` represents the data structure.
*   **Use Cases**: Represent the business logic operations (e.g., `CreateProduct`, `GetAllProducts`).
*   **Repository Pattern**: The `ProductRepository` interface and its implementation (`InMemoryProductRepository`) encapsulate data access logic.

## 5. Key Classes and Interactions

*   `Product`:  The core domain entity.
*   `ProductRepository`: Interface for data access.
*   `InMemoryProductRepository`:  Concrete implementation of `ProductRepository`.
*   Use case classes (e.g., `CreateProduct`, `GetAllProducts`): Orchestrate business logic, utilizing the `ProductRepository`.
*   `OpenApiConfig`: Configures the OpenAPI documentation for the API.

The use cases depend on the `ProductRepository` to perform CRUD operations on `Product` entities.  Spring manages the instantiation and wiring of these components. The `OpenApiConfig` class provides configuration details to generate and customize the OpenAPI documentation, including server URLs, contact information, and licensing.
