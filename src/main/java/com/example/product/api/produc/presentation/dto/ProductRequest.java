package com.example.product.api.produc.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for creating or updating a product")
public class ProductRequest {
    @Schema(description = "Name of the product", example = "Wireless Headphones", required = true)
    @NotBlank(message = "Product name is required")
    private String name;

    @Schema(description = "Detailed description of the product", example = "Noise-cancelling over-ear headphones")
    private String description;

    @Schema(description = "Price of the product in USD", example = "249.99", required = true)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "50", required = true)
    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
