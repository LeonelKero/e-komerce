package com.workbeattalent.orderservice.order;

import com.workbeattalent.orderservice.order.dto.OrderRequest;
import com.workbeattalent.orderservice.order.dto.OrderResponse;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    public Order toOrder(final OrderRequest request) {
        return Order.builder()
                .id(request.id())
                .reference(request.ref())
                .customerId(request.customerId())
                .totalAmount(request.amount())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse toOrderResponse(final Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
