package com.github.RehanKhan1704.events;

public record OrderCreatedEvent(
        Long orderId,
        Long productId,
        Integer quantity,
        Double amount
) {}
