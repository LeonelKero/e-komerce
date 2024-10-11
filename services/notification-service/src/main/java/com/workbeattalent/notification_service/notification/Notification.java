package com.workbeattalent.notification_service.notification;

import com.workbeattalent.notification_service.kafka.order.OrderConfirmation;
import com.workbeattalent.notification_service.kafka.payment.PaymentConfirmation;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {

    @Id
    private String id;
    private NotificationType type;
    private LocalDateTime date;
    private OrderConfirmation orderConfirmation;
    private PaymentConfirmation paymentConfirmation;

}
