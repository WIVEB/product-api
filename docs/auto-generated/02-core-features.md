# Product API Documentation

## 1. Overview

This document describes the core features of a Spring Boot reactive web application designed for managing a product inventory. The application provides CRUD operations for products and utilizes reactive programming with Spring WebFlux and Reactor. The application leverages an in-memory data store for product persistence. It exposes an OpenAPI endpoint for documentation.

## 2. Business Logic Components

The application is structured around use cases, a domain model, and a repository pattern.

### 2.1. Domain Model

The `Product` class represents the core domain model.

```java
package com.example.product.api.produc.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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

It contains the following attributes:

*   `id`:  Unique identifier (String).
*   `name`: Name of the product (String).
*   `description`: Description of the product (String).
*   `price`: Price of the product (BigDecimal).
*   `quantity`: Available quantity (Integer).

### 2.2. Repository Interface

The `ProductRepository` interface defines the contract for interacting with the product data store.

```java
package com.example.product.api.produc.domain.repository;

import com.example.product.api.produc.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Product> update(String id, Product product);
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}
```

It provides the following methods:

*   `findAll()`: Returns a `Flux` of all `Product` objects.
*   `findById(String id)`: Returns a `Mono` of a `Product` object matching the given ID.
*   `save(Product product)`: Saves a new `Product` object and returns a `Mono` of the saved object.
*   `update(String id, Product product)`: Updates an existing `Product` object with the given ID and returns a `Mono` of the updated object.
*   `deleteById(String id)`: Deletes a `Product` object by ID and returns a `Mono<Void>`.
*   `existsById(String id)`: Checks if a product exists by ID and returns `Mono<Boolean>`.

### 2.3. Use Cases

The application implements the following use cases as Spring `@Service` components:

*   **`CreateProduct`**: Creates a new product.
    *   `execute(Product product)`: Saves the product to the repository after generating a UUID for the id.
*   **`GetAllProducts`**: Retrieves all products.
    *   `execute()`: Returns a `Flux` of all products from the repository.
*   **`GetProductById`**: Retrieves a product by ID.
    *   `execute(String id)`: Returns a `Mono` of the product with the specified ID from the repository.
*   **`UpdateProduct`**: Updates an existing product.
    *   `execute(String id, Product product)`: Updates the product with the specified ID in the repository.
*   **`DeleteProduct`**: Deletes a product by ID.
    *   `execute(String id)`: Deletes the product with the specified ID from the repository.
*   **`PatchProduct`**: Partially updates an existing product.
    *   `execute(String id, Product partialProduct)`: Updates the product with the specified ID, only updating the provided fields.

## 3. Dependency Injection

Spring's dependency injection is used extensively.  Each use case is a `@Service` and depends on the `ProductRepository`. This dependency is injected via constructor injection using the `@RequiredArgsConstructor` annotation from Lombok.

Example:

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

## 4. Repository Implementation

The `InMemoryProductRepository` class provides an in-memory implementation of the `ProductRepository` interface.

```java
package com.example.product.api.produc.infrastructure.repository;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Initialize with default products
        Product laptop = Product.builder()
                .id("1")
                .name("Laptop")
                .description("High-performance laptop for professional use")
                .price(new BigDecimal("1299.99"))
                .quantity(15)
                .build();
       ... // other products

        products.put(laptop.getId(), laptop);
        products.put(smartphone.getId(), smartphone);
        ...//other products
    }

    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(products.values());
    }

    @Override
    public Mono<Product> findById(String id) {
        return Mono.justOrEmpty(products.get(id));
    }

    @Override
    public Mono<Product> save(Product product) {
        products.put(product.getId(), product);
        return Mono.just(product);
    }

    @Override
    public Mono<Product> update(String id, Product product) {
        return Mono.justOrEmpty(products.get(id))
                .flatMap(existingProduct -> {
                    product.setId(id);
                    products.put(id, product);
                    return Mono.just(product);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.justOrEmpty(products.remove(id))
                .then();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return Mono.just(products.containsKey(id));
    }
}
```

It uses a `ConcurrentHashMap` to store `Product` objects. The `@PostConstruct` annotation on the `init()` method ensures that the map is initialized with some default products when the application starts.

## 5. Key Classes and Interactions

*   **`Product`**:  The domain model representing a product.
*   **`ProductRepository`**: Interface for accessing and managing product data.
*   **`InMemoryProductRepository`**:  Concrete implementation of the `ProductRepository` interface using an in-memory map.
*   **Use Case Classes (`CreateProduct`, `GetAllProducts`, `GetProductById`, `UpdateProduct`, `DeleteProduct`, `PatchProduct`)**:  Implement the business logic for various product-related operations.
*   **`OpenApiConfig`**: Configuration class for OpenAPI documentation, defining server details, contact information, and license details.

The use case classes interact with the `ProductRepository` to perform CRUD operations on `Product` entities.  The `InMemoryProductRepository` provides a concrete implementation of the `ProductRepository` that stores product data in memory.  Spring manages the dependencies between these classes using dependency injection.

## 6. OpenAPI Configuration

The `OpenApiConfig` class configures the OpenAPI documentation for the application.

```java
package com.example.product.api.produc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productApiOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        Contact contact = new Contact();
        contact.setName("Product API Team");
        contact.setEmail("product-api@example.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Product Inventory API")
                .version("1.0.0")
                .description("A reactive REST API for managing product inventory. Built with Spring Boot WebFlux following Clean Architecture principles.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer));
    }
}
```

It defines a `productApiOpenAPI` bean that configures the OpenAPI documentation with server details, contact information, and license details.  The application documentation is available at `http://localhost:8080/swagger-ui.html`.
