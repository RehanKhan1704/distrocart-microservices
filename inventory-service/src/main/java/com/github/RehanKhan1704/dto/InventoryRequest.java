package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryRequest(
    @NotNull(message = "Product ID cannot be null")
    Long productId,
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    Integer quantity,
    @NotNull(message = "Reserved Quantity cannot be null")
    @Min(value = 0, message = "Reserved Quantity cannot be negative")
    Integer reservedQuantity
){
}