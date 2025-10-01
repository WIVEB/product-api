package com.example.product.api.produc.infrastructure.repository;

import com.example.product.api.produc.domain.model.Product;
import com.example.product.api.produc.domain.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private final Map<String, Product> products = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        // Initialize with default products
        Product laptop = Product.builder()
                .id("1")
                .name("Laptop")
                .description("High-performance laptop for professional use")
                .price(new BigDecimal("1299.99"))
                .quantity(15)
                .build();

        Product smartphone = Product.builder()
                .id("2")
                .name("Smartphone")
                .description("Latest model with advanced camera features")
                .price(new BigDecimal("899.99"))
                .quantity(30)
                .build();

        Product headphones = Product.builder()
                .id("3")
                .name("Wireless Headphones")
                .description("Noise-cancelling over-ear headphones")
                .price(new BigDecimal("249.99"))
                .quantity(50)
                .build();

        Product monitor = Product.builder()
                .id("4")
                .name("4K Monitor")
                .description("27-inch 4K UHD display")
                .price(new BigDecimal("449.99"))
                .quantity(20)
                .build();

        Product keyboard = Product.builder()
                .id("5")
                .name("Mechanical Keyboard")
                .description("RGB backlit mechanical keyboard")
                .price(new BigDecimal("129.99"))
                .quantity(40)
                .build();

        Product mouse = Product.builder()
                .id("6")
                .name("Wireless Mouse")
                .description("Ergonomic wireless mouse")
                .price(new BigDecimal("49.99"))
                .quantity(60)
                .build();

        Product tablet = Product.builder()
                .id("7")
                .name("Tablet")
                .description("10-inch tablet with stylus support")
                .price(new BigDecimal("599.99"))
                .quantity(25)
                .build();

        Product webcam = Product.builder()
                .id("8")
                .name("HD Webcam")
                .description("1080p webcam for video conferencing")
                .price(new BigDecimal("89.99"))
                .quantity(35)
                .build();

        Product speaker = Product.builder()
                .id("9")
                .name("Bluetooth Speaker")
                .description("Portable waterproof speaker")
                .price(new BigDecimal("79.99"))
                .quantity(45)
                .build();

        Product charger = Product.builder()
                .id("10")
                .name("USB-C Charger")
                .description("65W fast charging adapter")
                .price(new BigDecimal("39.99"))
                .quantity(100)
                .build();

        products.put(laptop.getId(), laptop);
        products.put(smartphone.getId(), smartphone);
        products.put(headphones.getId(), headphones);
        products.put(monitor.getId(), monitor);
        products.put(keyboard.getId(), keyboard);
        products.put(mouse.getId(), mouse);
        products.put(tablet.getId(), tablet);
        products.put(webcam.getId(), webcam);
        products.put(speaker.getId(), speaker);
        products.put(charger.getId(), charger);
    }

    @Override
    public Flux<Product> findAll() {
        return Flux.fromIterable(products.values());
    }

    @Override
    public Mono<Product> findById(String id) {
        return Mono.justOrEmpty(products.get(id));
    }

    @Override
    public Mono<Product> save(Product product) {
        products.put(product.getId(), product);
        return Mono.just(product);
    }

    @Override
    public Mono<Product> update(String id, Product product) {
        return Mono.justOrEmpty(products.get(id))
                .flatMap(existingProduct -> {
                    product.setId(id);
                    products.put(id, product);
                    return Mono.just(product);
                });
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return Mono.justOrEmpty(products.remove(id))
                .then();
    }

    @Override
    public Mono<Boolean> existsById(String id) {
        return Mono.just(products.containsKey(id));
    }
}
