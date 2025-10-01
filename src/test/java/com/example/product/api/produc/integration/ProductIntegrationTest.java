package com.example.product.api.produc.integration;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.presentation.dto.ProductPatchRequest;
import com.example.product.api.produc.presentation.dto.ProductRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void shouldGetAllProducts() {
        webTestClient.get()
                .uri("/api/v1/products")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Product.class)
                .hasSize(10);
    }

    @Test
    @Order(2)
    void shouldGetProductById() {
        webTestClient.get()
                .uri("/api/v1/products/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Laptop")
                .jsonPath("$.price").isEqualTo(1299.99);
    }

    @Test
    @Order(3)
    void shouldReturn404WhenProductNotFound() {
        webTestClient.get()
                .uri("/api/v1/products/999")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void shouldCreateNewProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("New Test Product")
                .description("Integration Test Product")
                .price(new BigDecimal("199.99"))
                .quantity(25)
                .build();

        webTestClient.post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").exists()
                .jsonPath("$.name").isEqualTo("New Test Product")
                .jsonPath("$.description").isEqualTo("Integration Test Product")
                .jsonPath("$.price").isEqualTo(199.99)
                .jsonPath("$.quantity").isEqualTo(25);
    }

    @Test
    @Order(5)
    void shouldReturn400WhenCreatingInvalidProduct() {
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
    @Order(6)
    void shouldUpdateProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("Updated Laptop")
                .description("Updated high-performance laptop")
                .price(new BigDecimal("1499.99"))
                .quantity(20)
                .build();

        webTestClient.put()
                .uri("/api/v1/products/3")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("3")
                .jsonPath("$.name").isEqualTo("Updated Laptop")
                .jsonPath("$.price").isEqualTo(1499.99)
                .jsonPath("$.quantity").isEqualTo(20);
    }

    @Test
    @Order(7)
    void shouldReturn404WhenUpdatingNonExistentProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("Non-existent Product")
                .description("This should fail")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();

        webTestClient.put()
                .uri("/api/v1/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(8)
    void shouldPatchProduct() {
        ProductPatchRequest request = ProductPatchRequest.builder()
                .name("Patched Headphones Name")
                .build();

        webTestClient.patch()
                .uri("/api/v1/products/4")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("4")
                .jsonPath("$.name").isEqualTo("Patched Headphones Name");
    }

    @Test
    @Order(9)
    void shouldPatchMultipleFieldsOfProduct() {
        ProductPatchRequest request = ProductPatchRequest.builder()
                .price(new BigDecimal("999.99"))
                .quantity(50)
                .build();

        webTestClient.patch()
                .uri("/api/v1/products/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("5")
                .jsonPath("$.price").isEqualTo(999.99)
                .jsonPath("$.quantity").isEqualTo(50);
    }

    @Test
    @Order(10)
    void shouldReturn404WhenPatchingNonExistentProduct() {
        ProductPatchRequest request = ProductPatchRequest.builder()
                .name("Non-existent")
                .build();

        webTestClient.patch()
                .uri("/api/v1/products/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void shouldDeleteProduct() {
        webTestClient.delete()
                .uri("/api/v1/products/6")
                .exchange()
                .expectStatus().isNoContent();

        // Verify product is deleted
        webTestClient.get()
                .uri("/api/v1/products/6")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(12)
    @DirtiesContext
    void shouldCompleteFullCRUDWorkflow() {
        // Create
        ProductRequest createRequest = ProductRequest.builder()
                .name("Workflow Test Product")
                .description("Testing full CRUD workflow")
                .price(new BigDecimal("299.99"))
                .quantity(100)
                .build();

        String productId = webTestClient.post()
                .uri("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(createRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Product.class)
                .returnResult()
                .getResponseBody()
                .getId();

        // Read
        webTestClient.get()
                .uri("/api/v1/products/" + productId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Workflow Test Product");

        // Update
        ProductRequest updateRequest = ProductRequest.builder()
                .name("Updated Workflow Product")
                .description("Updated description")
                .price(new BigDecimal("349.99"))
                .quantity(80)
                .build();

        webTestClient.put()
                .uri("/api/v1/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updateRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Updated Workflow Product");

        // Patch
        ProductPatchRequest patchRequest = ProductPatchRequest.builder()
                .quantity(60)
                .build();

        webTestClient.patch()
                .uri("/api/v1/products/" + productId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(patchRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.quantity").isEqualTo(60);

        // Delete
        webTestClient.delete()
                .uri("/api/v1/products/" + productId)
                .exchange()
                .expectStatus().isNoContent();

        // Verify deletion
        webTestClient.get()
                .uri("/api/v1/products/" + productId)
                .exchange()
                .expectStatus().isNotFound();
    }
}
