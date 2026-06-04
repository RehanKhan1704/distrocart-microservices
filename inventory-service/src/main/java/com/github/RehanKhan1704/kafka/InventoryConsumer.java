package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.events.PaymentSuccessEvent;
import com.github.RehanKhan1704.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(
            topics = "payment-success",
            groupId = "inventory-group"
    )
    public void consume(
            PaymentSuccessEvent event){

        inventoryService.reserveStock(
                event.orderId(),
                event.productId(),
                event.quantity()
        );
    }
}
