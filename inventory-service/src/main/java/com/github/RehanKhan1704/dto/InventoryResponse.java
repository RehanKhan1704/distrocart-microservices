package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InventoryResponse (
    @NotNull
    Long productId,
    @NotNull
    @Min(0)
    Integer quantity,
    Boolean inStock
){

    
}
