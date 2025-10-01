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
class UpdateProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private UpdateProduct updateProduct;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .name("Updated Product")
                .description("Updated Description")
                .price(new BigDecimal("79.99"))
                .quantity(20)
                .build();
    }

    @Test
    void execute_shouldUpdateProduct() {
        when(productRepository.update("1", product)).thenReturn(Mono.just(product));

        StepVerifier.create(updateProduct.execute("1", product))
                .expectNext(product)
                .verifyComplete();
    }

    @Test
    void execute_shouldReturnEmptyMonoWhenProductNotExists() {
        when(productRepository.update("999", product)).thenReturn(Mono.empty());

        StepVerifier.create(updateProduct.execute("999", product))
                .verifyComplete();
    }
}
