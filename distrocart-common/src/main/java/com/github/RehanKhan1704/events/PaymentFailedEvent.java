package com.github.RehanKhan1704.events;

public record PaymentFailedEvent(
        Long orderId,
        String reason
) {}
