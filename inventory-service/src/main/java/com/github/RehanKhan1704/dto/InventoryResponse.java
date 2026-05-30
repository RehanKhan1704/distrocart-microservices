package com.github.RehanKhan1704.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record InventoryResponse(

    @Schema(example = "1")
    Long id,

    @Schema(example = "1")
    Long productId,

    @Schema(example = "100")
    Integer quantity,

    @Schema(example = "true")
    Boolean inStock
){}
