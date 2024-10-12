package com.workbeattalent.notification_service.kafka;

import com.workbeattalent.notification_service.email.EmailService;
import com.workbeattalent.notification_service.kafka.order.OrderConfirmation;
import com.workbeattalent.notification_service.kafka.payment.PaymentConfirmation;
import com.workbeattalent.notification_service.notification.Notification;
import com.workbeattalent.notification_service.notification.NotificationRepository;
import com.workbeattalent.notification_service.notification.NotificationType;
import jakarta.mail.MessagingException;
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
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentNotification(final PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("INFO == Start Consuming payment {} of topic PAYMENT-TOPIC", paymentConfirmation);
        // Persist notification about payment
        this.repository.save(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build());
        // Then send email
        this.emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname(),
                paymentConfirmation.amount(), paymentConfirmation.orderRef());

        log.info("INFO == Finished consuming payment notification email");
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(final OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("INFO == Start Consuming order {} of topic ORDER-TOPIC", orderConfirmation);
        // Persist order notification
        this.repository.save(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .date(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build()
        );

        // Send Email
        this.emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                orderConfirmation.customer().firstname() + " " + orderConfirmation.customer().lastname(),
                orderConfirmation.totalAmount(),
                orderConfirmation.orderRef(),
                orderConfirmation.products()
        );
        log.info("INFO == Finished consuming order notification email");
    }
}
