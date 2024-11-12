package com.workbeattalent.notification.kafka.payment;

import java.math.BigDecimal;

public record PaymentConfirmation(
        String orderRef,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
