package com.github.RehanKhan1704.mapper;

import org.mapstruct.Mapper;

import com.github.RehanKhan1704.dto.PaymentResponse;
import com.github.RehanKhan1704.entity.Payment;


@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentResponse toResponse(Payment payment);
}