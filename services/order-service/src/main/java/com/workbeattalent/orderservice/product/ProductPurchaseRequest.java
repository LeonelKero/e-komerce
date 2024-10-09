package com.workbeattalent.orderservice.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID is required")
        Integer productId,
        @Positive(message = "Product quantity must be more than zero")
        Integer requestedQuantity
) {
}
