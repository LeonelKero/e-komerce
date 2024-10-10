package com.workbeattalent.orderservice.lines;

import com.workbeattalent.orderservice.lines.dto.OrderLineRequest;
import com.workbeattalent.orderservice.lines.dto.OrderLineResponse;
import com.workbeattalent.orderservice.order.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLine toOrderLine(final OrderLineRequest lineRequest) {
        return OrderLine.builder()
                .id(lineRequest.id())
                .order(Order.builder().id(lineRequest.orderId()).build())
                .productId(lineRequest.productId())
                .quantity(lineRequest.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(final OrderLine line) {
        return new OrderLineResponse(line.getId(), line.getQuantity());
    }
}
