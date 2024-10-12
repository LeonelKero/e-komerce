package com.workbeattalent.notification_service.email;

public enum EmailTemplates {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment Successfully processed"),
    ORDER_CONFIRMATION("order-confirmation.html", "Order Confirmation");

    private String template;
    private String subject;

    EmailTemplates(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return template;
    }

    public String getSubject() {
        return subject;
    }
}
