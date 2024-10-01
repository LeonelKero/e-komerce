package com.workbeatstalent.productservice.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Product name is required")
        String name,
        @NotNull(message = "Product description is required")
        String description,
        @NotNull(message = "Product quantity is required")
        @Positive(message = "Product quantity cannot be negative of zero")
        Integer quantity,
        @NotNull(message = "Product price is required")
        @Positive(message = "Product price cannot be negative or null")
        BigDecimal price,
        @NotNull(message = "Please specified in which category belongs this product")
        Integer categoryId
) {
}
