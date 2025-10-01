package com.example.product.api.produc.presentation.controller;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.presentation.dto.ProductPatchRequest;
import com.example.product.api.produc.presentation.dto.ProductRequest;
import com.example.product.api.produc.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@WebFluxTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GetAllProducts getAllProducts;

    @MockBean
    private GetProductById getProductById;

    @MockBean
    private CreateProduct createProduct;

    @MockBean
    private UpdateProduct updateProduct;

    @MockBean
    private PatchProduct patchProduct;

    @MockBean
    private DeleteProduct deleteProduct;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id("1")
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();
    }

    @Test
    void getAllProducts_shouldReturnAllProducts() {
        when(getAllProducts.execute()).thenReturn(Flux.just(product));

        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(1);
    }

    @Test
    void getProductById_shouldReturnProductWhenExists() {
        when(getProductById.execute("1")).thenReturn(Mono.just(product));

        webTestClient.get()
                .uri("/api/v1/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Test Product");
    }

    @Test
    void getProductById_shouldReturn404WhenNotExists() {
        when(getProductById.execute("999")).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/products/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("49.99"))
                .quantity(5)
                .build();

        when(createProduct.execute(any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void createProduct_shouldReturn400WhenInvalidRequest() {
        ProductRequest invalidRequest = ProductRequest.builder()
                .name("")
                .price(new BigDecimal("-10.00"))
                .quantity(-5)
                .build();

        webTestClient.post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("79.99"))
                .quantity(15)
                .build();

        when(updateProduct.execute(eq("1"), any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.put()
                .uri("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void updateProduct_shouldReturn404WhenNotExists() {
        ProductRequest request = ProductRequest.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("79.99"))
                .quantity(15)
                .build();

        when(updateProduct.execute(eq("999"), any(Product.class))).thenReturn(Mono.empty());

        webTestClient.put()
                .uri("/api/v1/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void patchProduct_shouldReturnPatchedProduct() {
        ProductPatchRequest request = ProductPatchRequest.builder()
                .name("Patched Name")
                .build();

        when(patchProduct.execute(eq("1"), any(Product.class))).thenReturn(Mono.just(product));

        webTestClient.patch()
                .uri("/api/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1");
    }

    @Test
    void patchProduct_shouldReturn404WhenNotExists() {
        ProductPatchRequest request = ProductPatchRequest.builder()
                .name("Patched Name")
                .build();

        when(patchProduct.execute(eq("999"), any(Product.class))).thenReturn(Mono.empty());

        webTestClient.patch()
                .uri("/api/v1/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteProduct_shouldReturnNoContent() {
        when(deleteProduct.execute("1")).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/api/v1/products/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}
