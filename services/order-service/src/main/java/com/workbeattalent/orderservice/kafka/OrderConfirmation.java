package com.workbeattalent.orderservice.kafka;

import com.workbeattalent.orderservice.customer.CustomerResponse;
import com.workbeattalent.orderservice.order.PaymentMethod;
import com.workbeattalent.orderservice.product.ProductPurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderRef,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<ProductPurchaseResponse> products
) {
}
