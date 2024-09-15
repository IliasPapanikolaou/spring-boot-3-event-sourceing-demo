package com.ipap.shippingservice.service;

import com.ipap.commonlibs.dao.OrderEvent;
import com.ipap.commonlibs.dao.OrderStatus;
import com.ipap.shippingservice.repository.OrderEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShippingEventService {

    private static final String ORDER_SHIPPED = "Order Shipped Successfully";
    private static final String ORDER_DELIVERED = "Order Delivered Successfully";

    private final OrderEventRepository orderEventRepository;

    //@KafkaListener(topics = "{#'${order.event.topicName}'}", groupId = "{#'${spring.kafka.consumer.group-id}'}")
    @KafkaListener(topics = "order-events", groupId = "shipping-service")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        if (OrderStatus.CONFIRMED.equals(orderEvent.getStatus())) {
            log.info("Received order event: {}", orderEvent);
            shipOrder(orderEvent.getOrderId());
            return;
        }
        log.info("Received but not confirmed - order event: {}", orderEvent);
    }

    // Ship order
    public void shipOrder(String orderId) {
        log.info("Shipping order: {}", orderId);
        OrderEvent orderEvent = new OrderEvent(orderId, OrderStatus.SHIPPED, ORDER_SHIPPED, LocalDateTime.now());
        orderEventRepository.save(orderEvent);
        deliverOrder(orderId);
    }

    // Deliver order
    public void deliverOrder(String orderId) {
        log.info("Delivering order: {}", orderId);
        OrderEvent orderEvent = new OrderEvent(orderId, OrderStatus.DELIVERED, ORDER_DELIVERED, LocalDateTime.now());
        orderEventRepository.save(orderEvent);
    }
}
