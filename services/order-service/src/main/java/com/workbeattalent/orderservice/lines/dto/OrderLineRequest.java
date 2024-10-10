package com.workbeattalent.orderservice.lines.dto;

public record OrderLineRequest(
        Long id,
        Long orderId, // should be order id?
        Integer productId,
        Integer quantity) {
}
