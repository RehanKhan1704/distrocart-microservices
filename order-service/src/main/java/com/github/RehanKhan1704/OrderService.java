package com.github.RehanKhan1704;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.github.RehanKhan1704.dto.orderRequest;
import com.github.RehanKhan1704.dto.orderResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final InventoryClient inventoryClient;

    @CircuitBreaker(
         name = "inventoryService",
         fallbackMethod = "failReqStock"
    )
    public String isInSttock(Long productId, Integer quantity){
      Boolean inStock = inventoryClient.getStocks(productId, quantity);
      if(Boolean.TRUE.equals(inStock)){
         return "Oder Placed";
      }
      return "Out of Stock";
    }

    public String failReqStock(){
      return "Inventory Service is down. Please try again later.";
    }

    public List<orderResponse> getById(Long id){
    List<Order> orders = orderRepository.findByProductId(id);
    return orders.stream().map(orderMapper::toResponse).collect(Collectors.toList());
 }

 public orderResponse createOrder(orderRequest request){
    Order order = orderMapper.toOrder(request);
    Order savedOrder = orderRepository.save(order);
    return orderMapper.toResponse(savedOrder);
 }

 public  Void deleteOrder(Long id){
    orderRepository.deleteById(id);
    return null;
 }
}