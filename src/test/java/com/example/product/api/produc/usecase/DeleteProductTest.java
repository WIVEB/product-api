package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProduct deleteProduct;

    @Test
    void execute_shouldDeleteProduct() {
        when(productRepository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier.create(deleteProduct.execute("1"))
                .verifyComplete();
    }
}
