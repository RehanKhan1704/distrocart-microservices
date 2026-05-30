package com.github.RehanKhan1704.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.RehanKhan1704.dto.ProductRequestDto;
import com.github.RehanKhan1704.dto.ProductResponseDto;
import com.github.RehanKhan1704.dto.ProductUpdateDto;
import com.github.RehanKhan1704.entity.Product;
import com.github.RehanKhan1704.exception.ProductNotFoundException;
import com.github.RehanKhan1704.mapper.ProductMapper;
import com.github.RehanKhan1704.repository.ProductRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request){
        Product product = productMapper.toEntity(request);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "product", key = "#id")
    public ProductResponseDto getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toResponse(product);
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public ProductResponseDto updateProduct(Long id, ProductUpdateDto product){
        Product existingProduct = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id)); 
        existingProduct.setName(product.name());
        existingProduct.setDescription(product.description());
        existingProduct.setPrice(product.price());

        return productMapper.toResponse(productRepository.save(existingProduct));
    }

    @Override
    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(Long id){
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable).map(productMapper::toResponse);
    }
}
