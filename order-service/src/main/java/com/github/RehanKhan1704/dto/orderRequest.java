package com.github.RehanKhan1704.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderRequest(

    @Schema(description = "Unique identifier of the product", example = "1")
    @NotNull(message = "Product ID cannot be null")
    Long productId,

    @Schema(description = "Quantity of the product ordered", example = "2")
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive")
    Integer quantity,

    @Schema(description = "Total price of the order", example = "19.98")
    @NotNull(message = "Total Price cannot be null")
    @Positive(message = "Total Price must be positive")
    Double totalPrice
){
    
}
