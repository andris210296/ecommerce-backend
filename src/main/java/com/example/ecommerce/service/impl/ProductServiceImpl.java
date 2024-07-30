package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotCreatedException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponseDto> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto getProductResponseById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    @Override
    public Product getProductById(Long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productResponseDto) {
        Product product = Product.builder()
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        return ProductResponseDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .build();
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDetails) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));

        oldProduct.setName(productDetails.getName());
        oldProduct.setPrice(productDetails.getPrice());
        oldProduct.setDescription(productDetails.getDescription());

        Product savedProduct = productRepository.save(oldProduct);

        return ProductResponseDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .build();
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));
        productRepository.delete(product);
        return true;
    }
}
