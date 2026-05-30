package com.github.RehanKhan1704.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

public record ProductRequestDto(

    @Schema(
        description = "Name of the product",
        example = "Laptop"
    )
    @NotBlank(message = "Name is required")
    String name,
    @Schema(
        description = "Description of the product",
        example = "A high-performance laptop suitable for gaming and work"
    )
    String description,
    @Schema(
        description = "Price of the product",
        example = "1000.00"
    )
    Double price
) {}