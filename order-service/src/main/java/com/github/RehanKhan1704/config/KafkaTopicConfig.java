package com.github.RehanKhan1704.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic orderCreatedTopic() {
        return TopicBuilder.name("order-created").build();
    }

    @Bean
    public NewTopic paymentSuccessTopic() {
        return TopicBuilder.name("payment-success").build();
    }

    @Bean
    public NewTopic paymentFailedTopic() {
        return TopicBuilder.name("payment-failed").build();
    }

    @Bean
    public NewTopic inventoryReservedTopic() {
        return TopicBuilder.name("inventory-reserved").build();
    }

    @Bean
    public NewTopic inventoryFailedTopic() {
        return TopicBuilder.name("inventory-failed").build();
    }
}