# Product Inventory API Documentation

This document provides comprehensive information about the Product Inventory API. This API allows you to manage products, including creating, retrieving, updating, and deleting them.

## Base URL

`/api/v1/products`

## OpenAPI/Swagger UI

This API utilizes Springdoc OpenAPI 3, which provides a Swagger UI for interactive API exploration.  You can access the Swagger UI at `/swagger-ui.html` relative to your application's root URL.

## Endpoints

### 1. Get All Products

*   **Method:** `GET`
*   **Endpoint:** `/api/v1/products`
*   **Description:** Retrieves a list of all products in the inventory.
*   **Request:**
    *   None
*   **Request Format:**
    *   N/A
*   **Request Validation Rules:**
    *   N/A
*   **Response:**
    *   **Success (200 OK):** Returns a list of `Product` objects.
*   **Response Format:**
    ```json
    [
      {
        "id": "product-id-1",
        "name": "Wireless Headphones",
        "description": "Noise-cancelling over-ear headphones",
        "price": 249.99,
        "quantity": 50
      },
      {
        "id": "product-id-2",
        "name": "Smartwatch",
        "description": "Fitness tracker and smartwatch",
        "price": 199.99,
        "quantity": 100
      }
    ]
    ```
*   **Error:**
    *   N/A (Typically no error responses for a simple `GET` all endpoint.)
*   **Reactive Type:** `Flux<Product>`
*   **Example Request:**

    ```bash
    curl -X GET http://localhost:8080/api/v1/products
    ```

### 2. Get Product by ID

*   **Method:** `GET`
*   **Endpoint:** `/api/v1/products/{id}`
*   **Description:** Retrieves a specific product by its ID.
*   **Request:**
    *   **Path Parameter:** `id` (String) - The unique identifier of the product.  Required.
*   **Request Format:**
    *   N/A
*   **Request Validation Rules:**
    *   N/A (validation typically performed in underlying service)
*   **Response:**
    *   **Success (200 OK):** Returns the `Product` object with the specified ID.
    *   **Error (404 Not Found):** Returned when a product with the specified ID does not exist.
*   **Response Format (Success):**
    ```json
    {
      "id": "product-id-1",
      "name": "Wireless Headphones",
      "description": "Noise-cancelling over-ear headphones",
      "price": 249.99,
      "quantity": 50
    }
    ```
*   **Reactive Type:** `Mono<ResponseEntity<Product>>`
*   **Example Request:**

    ```bash
    curl -X GET http://localhost:8080/api/v1/products/product-id-1
    ```

### 3. Create a New Product

*   **Method:** `POST`
*   **Endpoint:** `/api/v1/products`
*   **Description:** Creates a new product in the inventory.
*   **Request:**
    *   **Request Body:** `ProductRequest`
*   **Request Format:**
    ```json
    {
      "name": "New Product",
      "description": "A brand new product",
      "price": 99.99,
      "quantity": 20
    }
    ```
*   **Request Validation Rules:**
    *   `name`: Required, must not be blank.
    *   `price`: Required, must be greater than 0.
    *   `quantity`: Required, must be greater than or equal to 0.
*   **Response:**
    *   **Success (201 Created):** Returns the newly created `Product` object.
    *   **Error (400 Bad Request):** Returned when the request body is invalid (e.g., missing required fields, invalid data types).  Contains validation error details.
*   **Response Format (Success):**
    ```json
    {
      "id": "new-product-id",
      "name": "New Product",
      "description": "A brand new product",
      "price": 99.99,
      "quantity": 20
    }
    ```
*   **Reactive Type:** `Mono<Product>`
*   **Example Request:**

    ```bash
    curl -X POST \
      http://localhost:8080/api/v1/products \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "New Product",
        "description": "A brand new product",
        "price": 99.99,
        "quantity": 20
      }'
    ```

### 4. Update Product

*   **Method:** `PUT`
*   **Endpoint:** `/api/v1/products/{id}`
*   **Description:** Updates all fields of an existing product.  If the product with given ID does not exist it returns 404.
*   **Request:**
    *   **Path Parameter:** `id` (String) - The unique identifier of the product to update. Required.
    *   **Request Body:** `ProductRequest`
*   **Request Format:**
    ```json
    {
      "name": "Updated Product",
      "description": "An updated product description",
      "price": 149.99,
      "quantity": 30
    }
    ```
*   **Request Validation Rules:**
        *   `name`: Required, must not be blank.
        *   `price`: Required, must be greater than 0.
        *   `quantity`: Required, must be greater than or equal to 0.
*   **Response:**
    *   **Success (200 OK):** Returns the updated `Product` object.
    *   **Error (404 Not Found):** Returned when a product with the specified ID does not exist.
    *   **Error (400 Bad Request):** Returned when the request body is invalid.
*   **Response Format (Success):**
    ```json
    {
      "id": "product-id-1",
      "name": "Updated Product",
      "description": "An updated product description",
      "price": 149.99,
      "quantity": 30
    }
    ```
*   **Reactive Type:** `Mono<ResponseEntity<Product>>`
*   **Example Request:**

    ```bash
    curl -X PUT \
      http://localhost:8080/api/v1/products/product-id-1 \
      -H 'Content-Type: application/json' \
      -d '{
        "name": "Updated Product",
        "description": "An updated product description",
        "price": 149.99,
        "quantity": 30
      }'
    ```

### 5. Partially Update Product (Patch)

*   **Method:** `PATCH`
*   **Endpoint:** `/api/v1/products/{id}`
*   **Description:** Partially updates an existing product, allowing you to modify specific fields without affecting others.
*   **Request:**
    *   **Path Parameter:** `id` (String) - The unique identifier of the product to update. Required.
    *   **Request Body:** `ProductPatchRequest`
*   **Request Format:**
    ```json
    {
      "price": 175.50,
      "quantity": 75
    }
    ```
*   **Request Validation Rules:**
    *   `price` (optional): If provided, must be greater than 0.
    *   `quantity` (optional): If provided, must be greater than or equal to 0.
*   **Response:**
    *   **Success (200 OK):** Returns the updated `Product` object.
    *   **Error (404 Not Found):** Returned when a product with the specified ID does not exist.
    *   **Error (400 Bad Request):** Returned when the request body is invalid.
*   **Response Format (Success):**
    ```json
    {
      "id": "product-id-1",
      "name": "Wireless Headphones",
      "description": "Noise-cancelling over-ear headphones",
      "price": 175.50,
      "quantity": 75
    }
    ```
*   **Reactive Type:** `Mono<ResponseEntity<Product>>`
*   **Example Request:**

    ```bash
    curl -X PATCH \
      http://localhost:8080/api/v1/products/product-id-1 \
      -H 'Content-Type: application/json' \
      -d '{
        "price": 175.50,
        "quantity": 75
      }'
    ```

### 6. Delete Product

*   **Method:** `DELETE`
*   **Endpoint:** `/api/v1/products/{id}`
*   **Description:** Deletes a product from the inventory.
*   **Request:**
    *   **Path Parameter:** `id` (String) - The unique identifier of the product to delete. Required.
*   **Request Format:**
    *   N/A
*   **Request Validation Rules:**
    *   N/A (validation typically performed in underlying service)
*   **Response:**
    *   **Success (204 No Content):** Indicates successful deletion. No response body.
    *   **Error (404 Not Found):** Returned when a product with the specified ID does not exist.
*   **Reactive Type:** `Mono<ResponseEntity<Void>>`
*   **Example Request:**

    ```bash
    curl -X DELETE http://localhost:8080/api/v1/products/product-id-1
    ```

## Data Transfer Objects (DTOs)

### ProductRequest

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

### ProductPatchRequest

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

## Model

### Product

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer quantity;
}
```

## Error Handling

The API uses standard HTTP status codes to indicate the success or failure of a request. In case of errors, the API returns appropriate error messages.  Common error codes include:

*   **400 Bad Request:** Indicates that the request was malformed, often due to validation errors.
*   **404 Not Found:** Indicates that the requested resource (e.g., a product with a specific ID) was not found.
*   **500 Internal Server Error:** Indicates an unexpected error on the server. (Not explicitly shown in the controller but a possibility).

## Reactive Programming Notes

This API uses Spring WebFlux for reactive programming.  `Mono` represents a stream of 0 or 1 elements, useful for single-value responses like retrieving a specific product or creating/updating a product. `Flux` represents a stream of 0 or more elements, useful for returning a list of products. The `.map()` and `.defaultIfEmpty()` methods in the controller handle the transformation of the reactive streams into `ResponseEntity` objects for HTTP responses, including handling cases where a resource is not found.
