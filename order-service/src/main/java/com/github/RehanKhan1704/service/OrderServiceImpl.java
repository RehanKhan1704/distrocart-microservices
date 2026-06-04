package com.github.RehanKhan1704.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.RehanKhan1704.InventoryClient;
import com.github.RehanKhan1704.dto.OrderRequest;
import com.github.RehanKhan1704.dto.OrderResponse;
import com.github.RehanKhan1704.entity.Order;
import com.github.RehanKhan1704.entity.OrderStatus;
import com.github.RehanKhan1704.entity.PaymentStatus;
import com.github.RehanKhan1704.events.OrderCreatedEvent;
import com.github.RehanKhan1704.exception.OrderNotFoundException;
import com.github.RehanKhan1704.exception.OutOfStockException;
import com.github.RehanKhan1704.kafka.OrderProducer;
import com.github.RehanKhan1704.mapper.OrderMapper;
import com.github.RehanKhan1704.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.ws.rs.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;
    private final OrderProducer orderProducer;

    @CircuitBreaker(
         name = "inventoryService",
         fallbackMethod = "failReqStock"
    )
    public OrderResponse createOrder(OrderRequest request){
        System.out.println("createOrder() entered");
      Boolean stocks = inventoryClient.getStocks(request.productId(), request.quantity());
      System.out.println("createOrder() entered 111");
      if(!stocks){
         throw new OutOfStockException(request.productId());
      }
      System.out.println("createOrder() entered 222");
      Order order = orderMapper.toOrder(request);
      order.setPaymentStatus(PaymentStatus.PENDING);
      order.setOrderStatus(OrderStatus.PENDING);
      Order savedOrder = orderRepository.save(order);
      inventoryClient.reserveStock( savedOrder.getId(), request.productId(), request.quantity());  
      orderProducer.publishOrderCreated(
         new OrderCreatedEvent(
            savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity(),
                savedOrder.getTotalPrice()
         )
      );
      System.out.println("createOrder() entered 3333 ");
      return orderMapper.toResponse(savedOrder);
    }

    public OrderResponse failReqStock(OrderRequest request, Throwable throwable) {
    // Check if the exception was explicitly thrown by your out-of-stock business logic
    if (throwable instanceof OutOfStockException) {
        throw (OutOfStockException) throwable; 
    }

    // If it wasn't an OutOfStockException, the downstream inventory-service is physically down/unreachable
    throw new org.springframework.web.server.ResponseStatusException(
        HttpStatus.SERVICE_UNAVAILABLE, 
        "Inventory service is currently unavailable. Please try again later."
    );
}
      @Override
      @Transactional(readOnly = true)
    public OrderResponse getOrdersById(Long id){
    return orderRepository.findById(id)
        .map(orderMapper::toResponse)
        .orElseThrow(() -> new OrderNotFoundException(id));
 }

 @Override
   public Page<OrderResponse> getAllOrders(Pageable pageable){
      return orderRepository.findAll(pageable)
         .map(orderMapper::toResponse);
   }

@Override
 public  void deleteOrder(Long id){
      Order order = orderRepository.findById(id)
        .orElseThrow(() -> new OrderNotFoundException(id));
    orderRepository.delete(order);
 }
}