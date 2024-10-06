package com.workbeattalent.orderservice.order.dto;

import com.workbeattalent.orderservice.order.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,

        @NotNull(message = "The reference cannot be null")
        @NotEmpty(message = "The reference cannot be empty")
        @NotBlank(message = "The reference cannot be blank")
        String ref,

        @Positive(message = "Order amount cannot be zero or negative value")
        BigDecimal amount,

        @NotNull(message = "A payment method must be specified")
        PaymentMethod paymentMethod,

        @NotNull(message = "Customer ID is required")
        @NotEmpty(message = "Customer ID cannot be empty")
        @NotBlank(message = "Customer ID cannot be blank")
        String customerId,

        @NotNull(message = "At least one product must be purchased")
        @NotEmpty(message = "At least one product must be purchased")
        List<PurchaseRequest> products
) {
}
