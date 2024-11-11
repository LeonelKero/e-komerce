package com.workbeattalent.notification.kafka.order;

import java.math.BigDecimal;

public record Product(
        Integer id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
