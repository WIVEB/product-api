package com.example.product.api.produc.usecase;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PatchProduct {
    private final ProductRepository productRepository;

    public Mono<Product> execute(String id, Product partialProduct) {
        return productRepository.findById(id)
                .flatMap(existingProduct -> {
                    if (partialProduct.getName() != null) {
                        existingProduct.setName(partialProduct.getName());
                    }
                    if (partialProduct.getDescription() != null) {
                        existingProduct.setDescription(partialProduct.getDescription());
                    }
                    if (partialProduct.getPrice() != null) {
                        existingProduct.setPrice(partialProduct.getPrice());
                    }
                    if (partialProduct.getQuantity() != null) {
                        existingProduct.setQuantity(partialProduct.getQuantity());
                    }
                    return productRepository.update(id, existingProduct);
                });
    }
}
