package com.workbeattalent.notification_service.kafka.payment;

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
