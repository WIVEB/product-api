package com.example.product.api.produc.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product entity representing an item in the inventory")
public class Product {
    @Schema(description = "Unique identifier of the product", example = "1")
    private String id;

    @Schema(description = "Name of the product", example = "Laptop")
    private String name;

    @Schema(description = "Detailed description of the product", example = "High-performance laptop for professional use")
    private String description;

    @Schema(description = "Price of the product in USD", example = "1299.99")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "15")
    private Integer quantity;
}
