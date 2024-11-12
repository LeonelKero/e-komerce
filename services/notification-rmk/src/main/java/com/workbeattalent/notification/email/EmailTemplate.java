package com.workbeattalent.notification.email;

import lombok.Getter;

public enum EmailTemplate {
    PAYMENT_CONFIRMATION_TEMPLATE("payment-confirmation-template.html", "PAYMENT SUCCESSFULLY PROCESSED"),
    ORDER_CONFIRMATION_TEMPLATE("order-confirmation-template.html", "ORDER SUCCESSFULLY PLACED");

    @Getter
    private final String templateName;

    @Getter
    private final String subject;

    EmailTemplate(String templateName, String subject) {
        this.templateName = templateName;
        this.subject = subject;
    }
}
