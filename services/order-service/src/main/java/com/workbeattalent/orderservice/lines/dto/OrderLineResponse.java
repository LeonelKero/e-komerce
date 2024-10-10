package com.workbeattalent.orderservice.lines.dto;

public record OrderLineResponse(
        Long orderLineId,
        Integer quantity
) {
}
