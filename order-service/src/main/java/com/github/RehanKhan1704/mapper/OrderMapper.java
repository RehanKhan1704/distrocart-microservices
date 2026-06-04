package com.github.RehanKhan1704.mapper;

import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.dto.OrderRequest;
import com.github.RehanKhan1704.dto.OrderResponse;
import com.github.RehanKhan1704.entity.Order;

@Component
public class OrderMapper {

    public Order toOrder(OrderRequest request) {

        Order order = new Order();

        order.setProductId(request.productId());
        order.setQuantity(request.quantity());
        order.setTotalPrice(request.totalPrice());

        return order;
    }

    public OrderResponse toResponse(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getPaymentStatus(),
                order.getOrderStatus()
        );
    }
}