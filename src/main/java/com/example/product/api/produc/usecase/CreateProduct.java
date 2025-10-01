package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateProduct {
    private final ProductRepository productRepository;

    public Mono<Product> execute(Product product) {
        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }
}
