package com.workbeatstalent.productservice.product.dto;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
