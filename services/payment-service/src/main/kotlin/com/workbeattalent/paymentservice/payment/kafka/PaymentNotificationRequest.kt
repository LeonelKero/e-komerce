package com.workbeattalent.paymentservice.payment.kafka

import com.workbeattalent.paymentservice.payment.PaymentMethod
import java.math.BigDecimal

data class PaymentNotificationRequest(
    val orderRef: String,
    val amount: BigDecimal,
    val paymentMethod: PaymentMethod,
    val customerFirstname: String,
    val customerLastname: String,
    val customerEmail: String
)
