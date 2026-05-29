package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DefaultValue;

public record orderRequest(

    @NotNull(message = "User ID cannot be null")
    Long userId,

    @NotNull(message = "Product ID cannot be null")
    Long productId,

    @NotNull(message = "Quantity cannot be null")
    Integer quantity,

    @NotNull(message = "Total Price cannot be null")
    Double totalPrice,

    @DefaultValue("false")
    Boolean paymentStatus
){
    
}
