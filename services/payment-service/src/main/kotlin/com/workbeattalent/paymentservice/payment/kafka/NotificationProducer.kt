package com.workbeattalent.paymentservice.payment.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service

@Service
class NotificationProducer(private val kafkaTemplate: KafkaTemplate<String, PaymentNotificationRequest>) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun sendNotification(request: PaymentNotificationRequest) {
        logger.info("PAYMENT SERVICE -> Start sending payment notification with {}", request)
        val message = MessageBuilder
            .withPayload(request)
            .setHeader(KafkaHeaders.TOPIC, "payment-topic")
            .build()

        this.kafkaTemplate.send(message)
        logger.info("PAYMENT SERVICE -> Payment notification sent")
    }

}