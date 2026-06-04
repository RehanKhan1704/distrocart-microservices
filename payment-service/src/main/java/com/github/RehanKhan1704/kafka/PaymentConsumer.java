package com.github.RehanKhan1704.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.events.OrderCreatedEvent;
import com.github.RehanKhan1704.service.PaymentService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentConsumer {

    private final PaymentService service;

    @KafkaListener(
            topics = "order-created",
            groupId = "payment-group"
    )
    public void consume(
            OrderCreatedEvent event) {

        service.processPayment(event);
    }
}