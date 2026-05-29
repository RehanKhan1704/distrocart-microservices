package com.github.RehanKhan1704;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.RehanKhan1704.dto.orderRequest;
import com.github.RehanKhan1704.dto.orderResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<List<orderResponse>> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody orderRequest request){
        return ResponseEntity.ok(orderService.isInSttock(request.productId(), request.quantity()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id){
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
