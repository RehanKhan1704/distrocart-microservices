package com.github.RehanKhan1704.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.github.RehanKhan1704.dto.OrderRequest;
import com.github.RehanKhan1704.dto.OrderResponse;

public interface OrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrdersById(Long id);
    void deleteOrder(Long id);
    Page<OrderResponse> getAllOrders(Pageable pageable);
}
