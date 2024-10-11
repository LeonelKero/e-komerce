package com.workbeattalent.notification_service.kafka;

import com.workbeattalent.notification_service.kafka.order.OrderConfirmation;
import com.workbeattalent.notification_service.kafka.payment.PaymentConfirmation;
import com.workbeattalent.notification_service.notification.Notification;
import com.workbeattalent.notification_service.notification.NotificationRepository;
import com.workbeattalent.notification_service.notification.NotificationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    // Todo 1: Inject EmailService

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(final PaymentConfirmation paymentConfirmation) {
        log.info("Consuming payment {} of topic PAYMENT-TOPIC", paymentConfirmation);
        // Persist notification about payment
        this.repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build());
        // Then send email
        // Todo 2: Send Email
        log.info("Sending notification email");
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(final OrderConfirmation orderConfirmation) {
        log.info("Consuming order {} of topic ORDER-TOPIC", orderConfirmation);
        // Persist order notification
        this.repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // Todo 3: Send Email
        log.info("Sending notification email");
    }
}
