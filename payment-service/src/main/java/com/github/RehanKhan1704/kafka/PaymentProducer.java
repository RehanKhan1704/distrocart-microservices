package com.github.RehanKhan1704.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.events.PaymentFailedEvent;
import com.github.RehanKhan1704.events.PaymentSuccessEvent;

@Component
@RequiredArgsConstructor
public class PaymentProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSuccess(
            PaymentSuccessEvent event) {

        kafkaTemplate.send(
                "payment-success",
                event
        );
    }

    public void publishFailure(
            PaymentFailedEvent event) {

        kafkaTemplate.send(
                "payment-failed",
                event
        );
    }
}
