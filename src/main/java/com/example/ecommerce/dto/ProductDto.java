package com.example.ecommerce.dto;

import com.example.ecommerce.entity.Product;

public record ProductDto(String name, Double price, String description) {

    public Product toProduct() {
        return new Product(name, price, description);
    }
}
