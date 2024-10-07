package com.workbeattalent.orderservice.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
