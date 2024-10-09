package com.workbeattalent.orderservice.order;

import com.workbeattalent.orderservice.order.dto.OrderRequest;
import com.workbeattalent.orderservice.order.dto.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/orders"})
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Long> saveOrder(final @Valid @RequestBody OrderRequest order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(this.service.fetchAll());
    }


    @GetMapping(path = {"/{orderId}"})
    public ResponseEntity<OrderResponse> getOrder(final @PathVariable Long orderId) {
        return ResponseEntity.ok(this.service.fetchOrderById(orderId));
    }
}
