```markdown
# Product Inventory Management API

This document provides comprehensive information about the Product Inventory Management API.  This API allows you to manage product information, including retrieving, creating, updating, and deleting products.

This API is built using Spring Boot Reactive Web, leveraging `Mono` and `Flux` for asynchronous and non-blocking operations.

**Base URL:** `/api/v1/products`

## 1. Available Endpoints

| Method | Endpoint        | Description                                       |
|--------|-----------------|---------------------------------------------------|
| GET    | `/api/v1/products` | Retrieve all products.                            |
| GET    | `/api/v1/products/{id}` | Retrieve a specific product by its ID.          |
| POST   | `/api/v1/products` | Create a new product.                             |
| PUT    | `/api/v1/products/{id}` | Update all fields of an existing product.      |
| PATCH  | `/api/v1/products/{id}` | Partially update an existing product.         |
| DELETE | `/api/v1/products/{id}` | Delete a product by its ID.                  |

## 2. Request and Response Formats

### 2.1. ProductRequest (Create/Update Product)

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a product")
public class ProductRequest {
    @Schema(description = "Name of the product", example = "Wireless Headphones", required = true)
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "Detailed description of the product", example = "Noise-cancelling over-ear headphones")
    private String description;

    @Schema(description = "Price of the product in USD", example = "249.99", required = true)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "50", required = true)
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
```

**Fields:**

*   `name` (String, required):  Name of the product. Must not be blank.
*   `description` (String, optional): Detailed description of the product.
*   `price` (BigDecimal, required): Price of the product.  Must be greater than 0.
*   `quantity` (Integer, required):  Available quantity in stock. Must be greater than or equal to 0.

**Validation Rules:**

*   `name`: `@NotBlank(message = "Product name is required")`
*   `price`: `@NotNull(message = "Price is required")`, `@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")`
*   `quantity`: `@NotNull(message = "Quantity is required")`, `@Min(value = 0, message = "Quantity must be greater than or equal to 0")`

**Example Request Body (JSON):**

```json
{
  "name": "Bluetooth Speaker",
  "description": "Portable Bluetooth speaker with deep bass",
  "price": 79.99,
  "quantity": 100
}
```

### 2.2. ProductPatchRequest (Patch Product)

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for partially updating a product (all fields are optional)")
public class ProductPatchRequest {
    @Schema(description = "Name of the product", example = "Updated Headphones")
    private String name;

    @Schema(description = "Detailed description of the product", example = "Premium noise-cancelling headphones")
    private String description;

    @Schema(description = "Price of the product in USD", example = "299.99")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "100")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
```

**Fields:**

*   `name` (String, optional): Name of the product.
*   `description` (String, optional): Detailed description of the product.
*   `price` (BigDecimal, optional): Price of the product.  Must be greater than 0 if provided.
*   `quantity` (Integer, optional): Available quantity in stock. Must be greater than or equal to 0 if provided.

**Validation Rules:**

*   `price`: `@DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")`
*   `quantity`: `@Min(value = 0, message = "Quantity must be greater than or equal to 0")`

**Example Request Body (JSON):**

```json
{
  "price": 89.99,
  "quantity": 110
}
```

### 2.3. Product (Response)

```java
package com.example.product.api.produc.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
```

**Fields:**

*   `id` (String): Unique identifier of the product.
*   `name` (String): Name of the product.
*   `description` (String): Detailed description of the product.
*   `price` (BigDecimal): Price of the product.
*   `quantity` (Integer): Available quantity in stock.

**Example Response Body (JSON):**

```json
{
  "id": "654321a77b76613c69a6210e",
  "name": "Bluetooth Speaker",
  "description": "Portable Bluetooth speaker with deep bass",
  "price": 79.99,
  "quantity": 100
}
```

### 2.4 Error Responses

In case of errors, the API will return appropriate HTTP status codes and a JSON body (if applicable) with error details. The exact structure of the error response depends on the specific error.  For validation errors (e.g., invalid input in `ProductRequest`), the response will contain details about the invalid fields.

**Example Error Response (400 Bad Request - Validation Error):**

```json
{
    "timestamp": "2023-11-02T12:34:56.789+00:00",
    "status": 400,
    "error": "Bad Request",
    "path": "/api/v1/products",
    "message": "Validation failed for object='productRequest'. Error count: 1",
    "errors": [
        {
            "field": "name",
            "rejectedValue": null,
            "message": "Product name is required"
        }
    ]
}
```

**Example Error Response (404 Not Found):**

```json
{
  "timestamp": "2023-11-02T12:34:56.789+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Product not found"
}
```

## 3. Reactive Types

This API uses the following reactive types from Project Reactor:

*   `Mono<T>`: Represents an asynchronous sequence of 0 or 1 element.  Used for single-value responses (e.g., getting a product by ID, creating a product).
*   `Flux<T>`: Represents an asynchronous sequence of 0 to N elements. Used for multi-value responses (e.g., getting all products).

## 4. OpenAPI/Swagger UI Availability

This API is designed to be used with OpenAPI (Swagger).  You can access the OpenAPI UI, if configured, to view the API documentation and test the endpoints directly.  The URL to access the Swagger UI is typically:

`http://<your-host>:<your-port>/swagger-ui.html`

## 5. Example Requests

### 5.1. Get All Products

```bash
curl -X GET http://localhost:8080/api/v1/products
```

### 5.2. Get Product by ID

```bash
curl -X GET http://localhost:8080/api/v1/products/654321a77b76613c69a6210e
```

### 5.3. Create a New Product

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

### 5.4. Update Product

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

### 5.5. Patch Product

```bash
curl -X PATCH \
  http://localhost:8080/api/v1/products/654321a77b76613c69a6210e \
  -H 'Content-Type: application/json' \
  -d '{
    "price": 329.99
  }'
```

### 5.6. Delete Product

```bash
curl -X DELETE http://localhost:8080/api/v1/products/654321a77b76613c69a6210e
```
