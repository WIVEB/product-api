package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CreateProduct createProduct;

    private Product productWithoutId;

    @BeforeEach
    void setUp() {
        productWithoutId = Product.builder()
                .name("New Product")
                .description("New Description")
                .price(new BigDecimal("49.99"))
                .quantity(15)
                .build();
    }

    @Test
    void execute_shouldCreateProductWithGeneratedId() {
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product savedProduct = invocation.getArgument(0);
            return Mono.just(savedProduct);
        });

        StepVerifier.create(createProduct.execute(productWithoutId))
                .assertNext(product -> {
                    assertNotNull(product.getId());
                    assertNotNull(product.getName());
                })
                .verifyComplete();

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertNotNull(captor.getValue().getId());
    }
}
