package com.workbeattalent.orderservice.order.dto;

import com.workbeattalent.orderservice.lines.OrderLine;
import com.workbeattalent.orderservice.order.PaymentMethod;

import java.math.BigDecimal;
import java.util.Set;

public record OrderResponse(
        Long id,
        String reference,
        BigDecimal amount,
        PaymentMethod payment,
        String customer,
        Set<OrderLine> lines
) {
}
