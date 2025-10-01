package com.example.product.api.produc.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request object for partially updating a product (all fields are optional)")
public class ProductPatchRequest {
    @Schema(description = "Name of the product", example = "Updated Headphones")
    private String name;

    @Schema(description = "Detailed description of the product", example = "Premium noise-cancelling headphones")
    private String description;

    @Schema(description = "Price of the product in USD", example = "299.99")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @Schema(description = "Available quantity in stock", example = "100")
    @Min(value = 0, message = "Quantity must be greater than or equal to 0")
    private Integer quantity;
}
