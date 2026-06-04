package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.entity.Order;
import com.github.RehanKhan1704.entity.OrderStatus;
import com.github.RehanKhan1704.events.InventoryReservedEvent;
import com.github.RehanKhan1704.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InventoryReservedConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-reserved",
            groupId = "order-group"
    )
    public void consume(
            InventoryReservedEvent event) {

        Order order =
                orderRepository
                        .findById(event.orderId())
                        .orElseThrow();

        order.setOrderStatus(
                OrderStatus.CONFIRMED
        );

        orderRepository.save(order);
    }
}
