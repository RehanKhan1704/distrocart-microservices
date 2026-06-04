package com.github.RehanKhan1704.events;

public record InventoryFailedEvent(

        Long orderId,

        Long productId,

        String reason
) {
}