package com.workbeatstalent.productservice.product.dto;

public record ProductPurchaseRequest(
        Integer productId,
        Integer requestedQuantity
) {
}
