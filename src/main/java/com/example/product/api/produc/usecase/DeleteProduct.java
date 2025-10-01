package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DeleteProduct {
    private final ProductRepository productRepository;

    public Mono<Void> execute(String id) {
        return productRepository.deleteById(id);
    }
}
