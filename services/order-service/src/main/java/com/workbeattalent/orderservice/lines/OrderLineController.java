package com.workbeattalent.orderservice.lines;

import com.workbeattalent.orderservice.lines.dto.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/order-lines"})
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService lineService;

    @GetMapping(path = {"/order/{order-id}"})
    public ResponseEntity<List<OrderLineResponse>> allOrderLines(final @PathVariable(name = "order-id") Long orderId) {
        return ResponseEntity.ok(this.lineService.getAllOrderLinesByOrderId(orderId));
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<OrderLineResponse> lineById(final @PathVariable Long id) {
        return ResponseEntity.ok(this.lineService.getOrderLineById(id));
    }
}
