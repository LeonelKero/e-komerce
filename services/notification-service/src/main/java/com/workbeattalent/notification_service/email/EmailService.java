package com.workbeattalent.notification_service.email;

import com.workbeattalent.notification_service.kafka.order.Product;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendPaymentSuccessEmail(final String destination, final String customerName, final BigDecimal amount, final String orderRef) throws MessagingException {
        log.info("Preparing to send successful payment email to {}", destination);
        final var mimeMessage = this.mailSender.createMimeMessage();

        final var messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        messageHelper.setFrom("contact@workbeattalent.com");

        final var paymentTemplate = EmailTemplates.PAYMENT_CONFIRMATION.getTemplate();

        final var variables = new HashMap<String, Object>();
        variables.put("customerName", customerName);
        variables.put("amount", amount);
        variables.put("orderRef", orderRef);

        final var context = new Context();
        context.setVariables(variables);

        messageHelper.setSubject(EmailTemplates.PAYMENT_CONFIRMATION.getSubject());

        try {
            final var htmlTemplate = this.templateEngine.process(paymentTemplate, context);
            messageHelper.setText(htmlTemplate, true);
            messageHelper.setTo(destination);
            this.mailSender.send(mimeMessage);
            log.info("Payment email successfully sent to {}", destination);
        } catch (MessagingException e) {
            log.warn("WARNING == Unable to send successful payment email to {}, ERROR -> {}", destination, e.getLocalizedMessage());
        }
    }

    @Async
    public void sendOrderConfirmationEmail(final String destination, final String customerName, final BigDecimal amount, final String orderRef, final List<Product> products) throws MessagingException {
        log.info("Preparing to send order confirmation email to {}", destination);
        final var orderMimeMessage = this.mailSender.createMimeMessage();
        final var orderMessageHelper = new MimeMessageHelper(orderMimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        orderMessageHelper.setFrom("contact@workbeattalent.com");
        final var orderTemplate = EmailTemplates.ORDER_CONFIRMATION.getTemplate();
        final var orderVariables = new HashMap<String, Object>();
        orderVariables.put("customerName", customerName);
        orderVariables.put("totalAmount", amount);
        orderVariables.put("orderRef", orderRef);
        orderVariables.put("products", products);
        final var context = new Context();
        context.setVariables(orderVariables);
        orderMessageHelper.setSubject(EmailTemplates.ORDER_CONFIRMATION.getSubject());
        try {
            final var orderHtmlTemplate = this.templateEngine.process(orderTemplate, context);
            orderMessageHelper.setText(orderHtmlTemplate, true);
            orderMessageHelper.setTo(destination);

            this.mailSender.send(orderMimeMessage);
            log.info("Order confirmation email sent to {}", destination);
        } catch (MessagingException e) {
            log.warn("WARNING == Unable to send order confirmation email to {}, ERROR -> {}", destination, e.getLocalizedMessage());
        }
    }

}
