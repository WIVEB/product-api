package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatchProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private PatchProduct patchProduct;

    private Product existingProduct;

    @BeforeEach
    void setUp() {
        existingProduct = Product.builder()
                .id("1")
                .name("Original Name")
                .description("Original Description")
                .price(new BigDecimal("50.00"))
                .quantity(10)
                .build();
    }

    @Test
    void execute_shouldPatchNameOnly() {
        Product partialProduct = Product.builder()
                .name("New Name")
                .build();

        when(productRepository.findById("1")).thenReturn(Mono.just(existingProduct));
        when(productRepository.update(eq("1"), any(Product.class))).thenAnswer(invocation -> {
            Product updatedProduct = invocation.getArgument(1);
            return Mono.just(updatedProduct);
        });

        StepVerifier.create(patchProduct.execute("1", partialProduct))
                .assertNext(product -> {
                    assertEquals("New Name", product.getName());
                    assertEquals("Original Description", product.getDescription());
                    assertEquals(new BigDecimal("50.00"), product.getPrice());
                    assertEquals(10, product.getQuantity());
                })
                .verifyComplete();
    }

    @Test
    void execute_shouldPatchMultipleFields() {
        Product partialProduct = Product.builder()
                .name("New Name")
                .price(new BigDecimal("75.00"))
                .build();

        when(productRepository.findById("1")).thenReturn(Mono.just(existingProduct));
        when(productRepository.update(eq("1"), any(Product.class))).thenAnswer(invocation -> {
            Product updatedProduct = invocation.getArgument(1);
            return Mono.just(updatedProduct);
        });

        StepVerifier.create(patchProduct.execute("1", partialProduct))
                .assertNext(product -> {
                    assertEquals("New Name", product.getName());
                    assertEquals("Original Description", product.getDescription());
                    assertEquals(new BigDecimal("75.00"), product.getPrice());
                    assertEquals(10, product.getQuantity());
                })
                .verifyComplete();
    }

    @Test
    void execute_shouldPatchDescriptionOnly() {
        Product partialProduct = Product.builder()
                .description("New Description")
                .build();

        when(productRepository.findById("1")).thenReturn(Mono.just(existingProduct));
        when(productRepository.update(eq("1"), any(Product.class))).thenAnswer(invocation -> {
            Product updatedProduct = invocation.getArgument(1);
            return Mono.just(updatedProduct);
        });

        StepVerifier.create(patchProduct.execute("1", partialProduct))
                .assertNext(product -> {
                    assertEquals("Original Name", product.getName());
                    assertEquals("New Description", product.getDescription());
                    assertEquals(new BigDecimal("50.00"), product.getPrice());
                    assertEquals(10, product.getQuantity());
                })
                .verifyComplete();
    }

    @Test
    void execute_shouldReturnEmptyMonoWhenProductNotExists() {
        Product partialProduct = Product.builder()
                .name("New Name")
                .build();

        when(productRepository.findById("999")).thenReturn(Mono.empty());

        StepVerifier.create(patchProduct.execute("999", partialProduct))
                .verifyComplete();
    }
}
