package com.workbeattalent.orderservice.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderProducer {

    private final KafkaTemplate<String, OrderConfirmation> orderConfirmationKafkaTemplate;

    public void sendOrderConfirmation(final OrderConfirmation confirmation) {
        log.info("Sending order confirmation...");
        final var message = MessageBuilder
                .withPayload(confirmation)
                .setHeader(KafkaHeaders.TOPIC, "order-topic")
                .build();

        orderConfirmationKafkaTemplate.send(message);
    }

}
