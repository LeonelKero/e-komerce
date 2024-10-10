package com.workbeattalent.orderservice.payment;

import com.workbeattalent.orderservice.customer.CustomerResponse;
import com.workbeattalent.orderservice.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Long id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Long orderId,
        String orderRef,
        CustomerResponse customer
) {
}
