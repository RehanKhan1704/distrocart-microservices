package com.github.RehanKhan1704.mapper;

import org.springframework.stereotype.Component;

import com.github.RehanKhan1704.dto.ProductRequestDto;
import com.github.RehanKhan1704.dto.ProductResponseDto;
import com.github.RehanKhan1704.entity.Product;

@Component
public class ProductMapper {
    public Product toEntity(ProductRequestDto request){
        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        return product;
    }

public ProductResponseDto toResponse(Product product){
        ProductResponseDto response = new ProductResponseDto(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice()
        );
        return response;
    }
}
