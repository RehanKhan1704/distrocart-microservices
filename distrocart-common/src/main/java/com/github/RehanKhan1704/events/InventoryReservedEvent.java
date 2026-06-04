package com.github.RehanKhan1704.events;

public record InventoryReservedEvent(

        Long orderId,

        Long productId,

        Integer quantity
) {
}