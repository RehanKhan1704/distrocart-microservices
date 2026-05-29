package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.NotNull;

public record orderResponse(
    @NotNull
    Long userId,
    
    @NotNull
    Long productId,
    @NotNull
    Integer quantity,
    @NotNull
    Double totalPrice,
    Boolean paymentStatus

){
    
}
