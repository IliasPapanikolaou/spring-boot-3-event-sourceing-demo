package com.ipap.orderservice.service;

import com.ipap.commonlibs.dao.OrderEvent;
import com.ipap.commonlibs.dao.OrderStatus;
import com.ipap.commonlibs.dto.OrderRequest;
import com.ipap.commonlibs.dto.OrderResponse;
import com.ipap.orderservice.publisher.OrderEventKafkaPublisher;
import com.ipap.orderservice.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private static final String ORDER_CREATED = "Order created successfully";
    private static final String ORDER_CONFIRMED = "Order confirmed by agent";

    private final OrderEventRepository orderEventRepository;
    private final OrderEventKafkaPublisher orderEventKafkaPublisher;

    // Handle order creation
    public OrderResponse placeAnOrder(OrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString().split("-")[0];
        // Do request validation and business logic
        // Save and publish event order
        OrderEvent orderEvent = new OrderEvent(orderId, OrderStatus.CREATED, ORDER_CREATED, LocalDateTime.now());
        saveAndPublishEventOrder(orderEvent);
        log.info("Order created successfully");
        return new OrderResponse(orderId, OrderStatus.CREATED);
    }

    // Handle order confirmation
    public OrderResponse confirmOrder(String orderId) {
        OrderEvent orderEvent = new OrderEvent(orderId, OrderStatus.CONFIRMED, ORDER_CONFIRMED, LocalDateTime.now());
        saveAndPublishEventOrder(orderEvent);
        log.info("Order confirmed by agent");
        return new OrderResponse(orderId, OrderStatus.CONFIRMED);
    }

    private void saveAndPublishEventOrder(OrderEvent orderEvent) {
        // Save event
        orderEventRepository.save(orderEvent);
        // Publish kafka message
        if (OrderStatus.CONFIRMED.equals(orderEvent.getStatus())) {
            orderEventKafkaPublisher.sendOrderEvent(orderEvent);
        }
    }
}
