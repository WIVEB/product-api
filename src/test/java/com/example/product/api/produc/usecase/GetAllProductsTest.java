package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private GetAllProducts getAllProducts;

    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = Product.builder()
                .id("1")
                .name("Product 1")
                .description("Description 1")
                .price(new BigDecimal("10.00"))
                .quantity(5)
                .build();

        product2 = Product.builder()
                .id("2")
                .name("Product 2")
                .description("Description 2")
                .price(new BigDecimal("20.00"))
                .quantity(10)
                .build();
    }

    @Test
    void execute_shouldReturnAllProducts() {
        when(productRepository.findAll()).thenReturn(Flux.just(product1, product2));

        StepVerifier.create(getAllProducts.execute())
                .expectNext(product1)
                .expectNext(product2)
                .verifyComplete();
    }

    @Test
    void execute_shouldReturnEmptyFluxWhenNoProducts() {
        when(productRepository.findAll()).thenReturn(Flux.empty());

        StepVerifier.create(getAllProducts.execute())
                .verifyComplete();
    }
}
