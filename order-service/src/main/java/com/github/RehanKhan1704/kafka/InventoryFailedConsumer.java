package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.entity.Order;
import com.github.RehanKhan1704.entity.OrderStatus;
import com.github.RehanKhan1704.events.InventoryFailedEvent;
import com.github.RehanKhan1704.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryFailedConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-failed",
            groupId = "order-group"
    )
    public void consume(
            InventoryFailedEvent event) {

        Order order =
                orderRepository
                        .findById(event.orderId())
                        .orElseThrow();

        order.setOrderStatus(
                OrderStatus.CANCELLED
        );

        orderRepository.save(order);
    }
}
