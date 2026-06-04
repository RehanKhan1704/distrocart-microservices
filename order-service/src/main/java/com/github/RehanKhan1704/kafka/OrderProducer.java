package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.events.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderProducer {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishOrderCreated(OrderCreatedEvent event){
        System.out.println("Publishing order event: " + event);
        kafkaTemplate.send(
            "order-created",
            event
        );
        System.out.println("Event published");
    }
}
