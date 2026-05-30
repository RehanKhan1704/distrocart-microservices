package com.github.RehanKhan1704.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.github.RehanKhan1704.dto.ProductRequestDto;
import com.github.RehanKhan1704.dto.ProductResponseDto;
import com.github.RehanKhan1704.dto.ProductUpdateDto;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto request);
    ProductResponseDto getProductById(Long id);
    Page<ProductResponseDto> getAllProducts(Pageable pageable);
    ProductResponseDto updateProduct(Long id, ProductUpdateDto product);
    void deleteProduct(Long id);
}
