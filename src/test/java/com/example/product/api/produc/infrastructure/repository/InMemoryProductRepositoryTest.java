package com.example.product.api.produc.infrastructure.repository;

import com.example.product.api.produc.domain.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryProductRepositoryTest {

    private InMemoryProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryProductRepository();
        repository.init();
    }

    @Test
    void findAll_shouldReturnAllProducts() {
        StepVerifier.create(repository.findAll())
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnProductWhenExists() {
        StepVerifier.create(repository.findById("1"))
                .assertNext(product -> {
                    assertEquals("1", product.getId());
                    assertEquals("Laptop", product.getName());
                })
                .verifyComplete();
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        StepVerifier.create(repository.findById("999"))
                .verifyComplete();
    }

    @Test
    void save_shouldAddNewProduct() {
        Product newProduct = Product.builder()
                .id("100")
                .name("New Product")
                .description("Test Description")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();

        StepVerifier.create(repository.save(newProduct))
                .assertNext(product -> {
                    assertEquals("100", product.getId());
                    assertEquals("New Product", product.getName());
                })
                .verifyComplete();

        StepVerifier.create(repository.findById("100"))
                .assertNext(product -> assertEquals("New Product", product.getName()))
                .verifyComplete();
    }

    @Test
    void update_shouldUpdateExistingProduct() {
        Product updatedProduct = Product.builder()
                .name("Updated Laptop")
                .description("Updated description")
                .price(new BigDecimal("1499.99"))
                .quantity(20)
                .build();

        StepVerifier.create(repository.update("1", updatedProduct))
                .assertNext(product -> {
                    assertEquals("1", product.getId());
                    assertEquals("Updated Laptop", product.getName());
                    assertEquals(new BigDecimal("1499.99"), product.getPrice());
                })
                .verifyComplete();
    }

    @Test
    void update_shouldReturnEmptyWhenProductNotExists() {
        Product updatedProduct = Product.builder()
                .name("Non-existent Product")
                .description("Description")
                .price(new BigDecimal("99.99"))
                .quantity(10)
                .build();

        StepVerifier.create(repository.update("999", updatedProduct))
                .verifyComplete();
    }

    @Test
    void deleteById_shouldRemoveProduct() {
        StepVerifier.create(repository.deleteById("1"))
                .verifyComplete();

        StepVerifier.create(repository.findById("1"))
                .verifyComplete();
    }

    @Test
    void deleteById_shouldCompleteEvenWhenProductNotExists() {
        StepVerifier.create(repository.deleteById("999"))
                .verifyComplete();
    }

    @Test
    void existsById_shouldReturnTrueWhenProductExists() {
        StepVerifier.create(repository.existsById("1"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void existsById_shouldReturnFalseWhenProductNotExists() {
        StepVerifier.create(repository.existsById("999"))
                .expectNext(false)
                .verifyComplete();
    }
}
