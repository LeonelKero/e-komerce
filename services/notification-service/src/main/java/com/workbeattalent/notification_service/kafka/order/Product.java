package com.workbeattalent.notification_service.kafka.order;

import java.math.BigDecimal;

public record Product(
        Integer id,
        String name,
        String description,
        Integer availableQuantity,
        BigDecimal price
) {
}
