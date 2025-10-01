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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetProductById getProductById;

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
    void execute_shouldReturnProductWhenExists() {
        when(productRepository.findById("1")).thenReturn(Mono.just(product));

        StepVerifier.create(getProductById.execute("1"))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void execute_shouldReturnEmptyMonoWhenProductNotExists() {
        when(productRepository.findById("999")).thenReturn(Mono.empty());

        StepVerifier.create(getProductById.execute("999"))
                .verifyComplete();
    }
}
