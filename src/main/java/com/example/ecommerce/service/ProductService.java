package com.example.ecommerce.service;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;

import java.util.List;

public interface ProductService {

        List<Product> getAllProducts();

        Product getProductById(Long id);

        Product createProduct(ProductDto product);

        Product updateProduct(Long id, ProductDto product);

        boolean deleteProduct(Long id);
}
