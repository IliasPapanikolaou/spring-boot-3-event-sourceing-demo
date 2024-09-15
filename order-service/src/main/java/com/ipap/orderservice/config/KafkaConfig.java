package com.ipap.orderservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Value("${order.event.topicName}")
    private String topicName;

    @Bean
    public NewTopic createOrderTopic() {
        return new NewTopic(topicName, 3, (short) 1);
    }
}
