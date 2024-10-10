package com.workbeattalent.orderservice.order;

import com.workbeattalent.orderservice.customer.CustomerClient;
import com.workbeattalent.orderservice.customer.CustomerResponse;
import com.workbeattalent.orderservice.exceptions.BusinessException;
import com.workbeattalent.orderservice.exceptions.OrderNotFoundException;
import com.workbeattalent.orderservice.kafka.KafkaOrderProducer;
import com.workbeattalent.orderservice.kafka.OrderConfirmation;
import com.workbeattalent.orderservice.lines.OrderLineService;
import com.workbeattalent.orderservice.lines.dto.OrderLineRequest;
import com.workbeattalent.orderservice.order.dto.OrderRequest;
import com.workbeattalent.orderservice.order.dto.OrderResponse;
import com.workbeattalent.orderservice.payment.PaymentClient;
import com.workbeattalent.orderservice.payment.PaymentRequest;
import com.workbeattalent.orderservice.product.ProductClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;

    private final OrderRepository repository;
    private final OrderMapper orderMapper;
    private final OrderLineService lineService;
    private final KafkaOrderProducer orderProducer;

    public Long save(final OrderRequest request) {
        log.info("Preparing to save order");
        // Check if customer exists [OpenFeign] -> CALL TO CUSTOMER SERVICE
        log.info("Calling CUSTOMER SERVICE for details...");
        final var customer = this.customerClient.findCustomer(request.customerId())
                .orElseThrow(() -> new BusinessException("Unable to perform order for customer with ID " + request.customerId()));

        // Call product-service for purchase [RestTemplate] -> CALL TO PRODUCT SERVICE
        log.info("Calling PRODUCT SERVICE for details...");
        final var purchasedProductList = this.productClient.getPurchases(request.products());

        // Persist order
        final var savedOrder = this.repository.save(orderMapper.toOrder(request));

        request.products().forEach(purchaseRequest -> this.lineService.saveLine(
                new OrderLineRequest(
                        null,
                        savedOrder.getId(),
                        purchaseRequest.productId(),
                        purchaseRequest.requestedQuantity()
                )
        ));

        // Initiate payment [OpenFeign] -> CALL TO PAYMENT SERVICE
        final var paymentId = this.paymentClient.processPayment(new PaymentRequest(
                null,
                request.amount(),
                request.paymentMethod(),
                savedOrder.getId(),
                savedOrder.getReference(),
                new CustomerResponse(customer.id(), customer.firstname(), customer.lastname(), customer.email())
        ));

        // Notify customer (*) via kafka -> SEND MESSAGE TO THE NOTIFICATION SERVICE
        log.info("Calling NOTIFICATION SERVICE...");
        this.orderProducer.sendOrderConfirmation(new OrderConfirmation(
                request.ref(),
                request.amount(),
                request.paymentMethod(),
                customer,
                purchasedProductList
        ));

        log.info("Order saved!");
        return savedOrder.getId();
    }

    public OrderResponse fetchOrderById(final Long id) {
        return this.repository.findById(id)
                .map(this.orderMapper::toOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID " + id + " not found"));
    }

    public List<OrderResponse> fetchAll() {
        return this.repository.findAll()
                .stream()
                .map(this.orderMapper::toOrderResponse)
                .toList();
    }
}
