package com.github.RehanKhan1704;

import org.mapstruct.Mapper;

import com.github.RehanKhan1704.dto.orderRequest;
import com.github.RehanKhan1704.dto.orderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(orderRequest request);
    
    orderResponse toResponse(Order order);

}
