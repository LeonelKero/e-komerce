package com.workbeattalent.paymentservice.payment.mapper

import com.workbeattalent.paymentservice.payment.Payment
import com.workbeattalent.paymentservice.payment.dto.PaymentRequest
import org.springframework.stereotype.Service

@Service
class PaymentMapper {

    fun toPayment(request: PaymentRequest): Payment {
        return Payment(
            id = null,
            amount = request.amount,
            paymentMethod = request.paymentMethod,
            orderId = request.orderId,
        )
    }

}