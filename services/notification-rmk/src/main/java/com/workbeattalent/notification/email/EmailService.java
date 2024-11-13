package com.workbeattalent.notification.email;

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

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendSuccessPaymentEmail(
            final String destination,
            final String orderReference,
            final BigDecimal orderAmount,
            final String customerName) throws MessagingException {
        final var mimeMessage = this.mailSender.createMimeMessage();
        final var mimeHelper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_RELATED,
                StandardCharsets.UTF_8.name());
        mimeHelper.setFrom("team@workbeatstalent.com");

        final var paymentConfirmationEmailTemplate = EmailTemplate.PAYMENT_CONFIRMATION_TEMPLATE.getTemplateName();

        final var variables = new HashMap<String, Object>();
        variables.put("customerName", customerName);
        variables.put("totalAmount", orderAmount);
        variables.put("orderReference", orderReference);

        final var thymleafContext = new Context();
        thymleafContext.setVariables(variables);

        mimeHelper.setSubject(EmailTemplate.PAYMENT_CONFIRMATION_TEMPLATE.getSubject());
        try {
            final var htmlTemplate = this.templateEngine.process(paymentConfirmationEmailTemplate, thymleafContext);
            mimeHelper.setText(htmlTemplate, true);
            mimeHelper.setTo(destination);
            this.mailSender.send(mimeMessage);
            log.info("INFO - Payment Confirmation Email sent to {}", destination);
        } catch (MessagingException e) {
            log.warn("WARNING - Unable to send email to {} with error {}", destination, e.getLocalizedMessage());
        }
    }
}
