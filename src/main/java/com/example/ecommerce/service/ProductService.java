package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

        List<ProductResponseDto> getAllProducts();

        ProductResponseDto getProductResponseById(Long id);

        Product getProductById(Long id);

        ProductResponseDto createProduct(ProductRequestDto product);

        ProductResponseDto updateProduct(Long id, ProductRequestDto product);

        boolean deleteProduct(Long id);
}
