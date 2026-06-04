package com.github.RehanKhan1704.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.RehanKhan1704.dto.ProductRequestDto;
import com.github.RehanKhan1704.dto.ProductResponseDto;
import com.github.RehanKhan1704.dto.ProductUpdateDto;
import com.github.RehanKhan1704.service.ProductService;
import com.github.RehanKhan1704.service.ProductServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Product API", description = "API for managing products in Distrocart")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController{

    private final ProductService productService;

    @Operation(
        summary = "Get Product By Id",
        description = "Fetch a product using its unique id"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Product Found"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Product Not Found"
        )
})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(
        summary = "Get All Products",
        description = "Fetch all products with pagination"
)
   @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(Pageable pageable){
        return ResponseEntity.ok(
                productService.getAllProducts(pageable)
        );
    }

    @Operation(
        summary = "Create Product",
        description = "Create a new product"
)
@ApiResponse(
        responseCode = "201",
        description = "Product Created Successfully"
)
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto product){
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @Operation(
        summary = "Update Product",
        description = "Update an existing product"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "200",
                description = "Product Updated"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Product Not Found"
        )
})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductUpdateDto product){
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @Operation(
        summary = "Delete Product",
        description = "Delete product by id"
)
@ApiResponses({
        @ApiResponse(
                responseCode = "204",
                description = "Deleted Successfully"
        ),
        @ApiResponse(
                responseCode = "404",
                description = "Product Not Found"
        )
})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
}
