package com.github.RehanKhan1704.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InventoryRequest(
    
    @Schema(
        example = "1",
        description = "Product Id"
    )
    @NotNull(message = "Product Id is required")
    Long productId,

    @Schema(
        example = "100",
        description = "Available Stock Quantity"
    )
    @Positive(message = "Quantity must be positive")
    Integer quantity
){
}