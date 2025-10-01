package com.example.product.api.produc.presentation.controller;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.presentation.dto.ProductPatchRequest;
import com.example.product.api.produc.presentation.dto.ProductRequest;
import com.example.product.api.produc.usecase.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product", description = "Product inventory management API")
public class ProductController {

    private final GetAllProducts getAllProducts;
    private final GetProductById getProductById;
    private final CreateProduct createProduct;
    private final UpdateProduct updateProduct;
    private final PatchProduct patchProduct;
    private final DeleteProduct deleteProduct;

    @Operation(summary = "Get all products", description = "Retrieve a list of all products in the inventory")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all products")
    @GetMapping
    public Flux<Product> getAllProducts() {
        return getAllProducts.execute();
    }

    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Product>> getProductById(
            @Parameter(description = "Product ID", required = true) @PathVariable String id) {
        return getProductById.execute(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new product", description = "Add a new product to the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        return createProduct.execute(product);
    }

    @Operation(summary = "Update product", description = "Update all fields of an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Product>> updateProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable String id,
            @Valid @RequestBody ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        return updateProduct.execute(id, product)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Partially update product", description = "Update specific fields of an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PatchMapping("/{id}")
    public Mono<ResponseEntity<Product>> patchProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable String id,
            @Valid @RequestBody ProductPatchRequest request) {
        Product partialProduct = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        return patchProduct.execute(id, partialProduct)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete product", description = "Remove a product from the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(
            @Parameter(description = "Product ID", required = true) @PathVariable String id) {
        return deleteProduct.execute(id)
                .then(Mono.just(ResponseEntity.noContent().<Void>build()));
    }
}
