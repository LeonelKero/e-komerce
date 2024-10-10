package com.workbeattalent.paymentservice.payment

import com.workbeattalent.paymentservice.payment.dto.PaymentRequest
import com.workbeattalent.paymentservice.payment.kafka.NotificationProducer
import com.workbeattalent.paymentservice.payment.kafka.PaymentNotificationRequest
import com.workbeattalent.paymentservice.payment.mapper.PaymentMapper
import org.springframework.stereotype.Service

@Service
class PaymentService(
    private val paymentRepository: PaymentRepository,
    private val mapper: PaymentMapper,
    private val notificationProducer: NotificationProducer
) {

    fun createPayment(request: PaymentRequest): Long {
        // Persist payment
        val payment = this.paymentRepository.save(mapper.toPayment(request))
        // Send message to the notification service - through Kafka
        notificationProducer.sendNotification(
            PaymentNotificationRequest(
                request.orderRef,
                request.amount,
                request.paymentMethod,
                request.customer.firstname,
                request.customer.lastname,
                request.customer.email
            )
        )
        // Return payment ID
        return payment.id!!
    }

}