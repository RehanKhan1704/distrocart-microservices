package com.github.RehanKhan1704.mapper;
import com.github.RehanKhan1704.dto.InventoryRequest;
import com.github.RehanKhan1704.dto.InventoryResponse;
import com.github.RehanKhan1704.entity.Inventory;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryRequest request);
    @Mapping(
        target = "inStock",
        expression = "java((inventory.getQuantity() -inventory.getReservedQuantity()) > 0)"
    )
    InventoryResponse toResponseDto(Inventory inventory);
}
