package com.example.product.api.produc.domain.repository;

import com.example.product.api.produc.domain.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Flux<Product> findAll();
    Mono<Product> findById(String id);
    Mono<Product> save(Product product);
    Mono<Product> update(String id, Product product);
    Mono<Void> deleteById(String id);
    Mono<Boolean> existsById(String id);
}
