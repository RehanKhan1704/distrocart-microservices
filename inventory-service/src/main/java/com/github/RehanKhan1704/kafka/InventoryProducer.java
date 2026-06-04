package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.events.InventoryFailedEvent;
import com.github.RehanKhan1704.events.InventoryReservedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishReserved(
            InventoryReservedEvent event) {

        kafkaTemplate.send(
                "inventory-reserved",
                event
        );
    }

    public void publishFailed(
            InventoryFailedEvent event) {

        kafkaTemplate.send(
                "inventory-failed",
                event
        );
    }
}
