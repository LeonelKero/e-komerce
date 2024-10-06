package com.workbeattalent.orderservice.order.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product ID is required")
        Integer id,
        @Positive(message = "Product quantity must be more than zero")
        Integer quantity
) {
}
