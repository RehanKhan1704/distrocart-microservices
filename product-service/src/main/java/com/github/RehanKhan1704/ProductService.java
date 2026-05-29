package com.github.RehanKhan1704;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    @Cacheable(value = "product", key = "#p0")
    public Product getById(long id){
        return productRepository.findById(id)
                .orElseThrow (() -> new RuntimeException("Product does not exist with id: "+id));
    }

    @CacheEvict(value = "product", key = "#p0")
    public Product updateProduct(long id, Product product){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow (() -> new RuntimeException("Product does not exist with id: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    @CacheEvict(value = "product", key = "#id")
    public void deleteProduct(long id){
        productRepository.deleteById(id);
    }

    public Page<Product> getAllProducts(Pageable pageable){
        return productRepository.findAll(pageable);
    }

    public List<Product> saveAllProducts(List<Product> products){
        return productRepository.saveAll(products);
    }
}
