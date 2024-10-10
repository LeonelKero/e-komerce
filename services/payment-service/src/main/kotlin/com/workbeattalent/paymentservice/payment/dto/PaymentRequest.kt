package com.workbeattalent.paymentservice.payment.dto

import com.workbeattalent.paymentservice.payment.PaymentMethod
import java.math.BigDecimal

data class PaymentRequest(
    val id: Long?,
    val amount: BigDecimal,
    val paymentMethod: PaymentMethod,
    val orderId: Long,
    val orderRef: String,
    val customer: Customer
)
