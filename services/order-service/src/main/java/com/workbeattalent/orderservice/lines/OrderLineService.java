package com.workbeattalent.orderservice.lines;

import com.workbeattalent.orderservice.exceptions.OrderLineNotFoundException;
import com.workbeattalent.orderservice.lines.dto.OrderLineRequest;
import com.workbeattalent.orderservice.lines.dto.OrderLineResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public Long saveLine(final OrderLineRequest newLine) {
        return this.orderLineRepository
                .save(orderLineMapper.toOrderLine(newLine))
                .getId();
    }

    public List<OrderLineResponse> getAllOrderLinesByOrderId(final Long order) {
        return this.orderLineRepository.findAllByOrderId(order)
                .stream()
                .map(this.orderLineMapper::toOrderLineResponse)
                .toList();
    }

    public OrderLineResponse getOrderLineById(final Long lineId) {
        return this.orderLineRepository.findById(lineId)
                .map(this.orderLineMapper::toOrderLineResponse)
                .orElseThrow(() -> new OrderLineNotFoundException("Order line ID " + lineId + " not found"));
    }

}
