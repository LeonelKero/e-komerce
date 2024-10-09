package com.workbeattalent.orderservice.order.dto;

import com.workbeattalent.orderservice.order.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod payment,
        String customer
) {
}
