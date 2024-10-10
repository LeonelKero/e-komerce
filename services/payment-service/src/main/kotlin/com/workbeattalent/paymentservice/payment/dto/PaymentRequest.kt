package com.workbeattalent.paymentservice.payment.dto

import com.workbeattalent.paymentservice.payment.PaymentMethod
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class PaymentRequest(
    val id: Long?,
    @Positive(message = "Amount requires a positive value")
    val amount: BigDecimal,
    val paymentMethod: PaymentMethod,
    @NotNull(message = "Order identifier required")
    val orderId: Long,
    @NotNull(message = "Order reference required")
    val orderRef: String,
    val customer: Customer
)
