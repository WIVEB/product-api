## Spring Boot Reactive Application Architecture

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