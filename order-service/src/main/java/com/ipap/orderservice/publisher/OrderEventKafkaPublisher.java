package com.ipap.orderservice.publisher;

import com.ipap.commonlibs.dao.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventKafkaPublisher {

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Value("${order.event.topicName}")
    private String topicName;

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(topicName, orderEvent);
    }
}
