package com.workbeattalent.paymentservice.payment.kafka

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaPaymentTopic {

    @Bean
    fun paymentTopic() = TopicBuilder.name("payment-topic").build()

}