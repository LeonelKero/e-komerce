package com.workbeatstalent.productservice.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "Product ID must be specified")
        Integer productId,
        @Positive(message = "Requested quantity cannot be zero")
        Integer requestedQuantity
) {
}
