package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotCreatedException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        logger.info("Fetching all products");

        List<Product> products = productRepository.findAll();
        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(product -> ProductResponseDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .build())
                .collect(Collectors.toList());

        logger.debug("Fetched {} products", productResponseDtos.size());
        logger.info("Fetched products successfully");
        return productResponseDtos;
    }

    @Override
    public ProductResponseDto getProductResponseById(Long id) {
        logger.info("Fetching product by ID");
        logger.debug("Fetching product with ID: {}", id);

        Product product = findProductById(id);
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();

        logger.debug("Fetched product: {}", productResponseDto);
        logger.info("Fetched product by id successfully");
        return productResponseDto;
    }

    @Override
    public Product getProductById(Long id) {
        logger.info("Fetching product entity by ID");
        logger.debug("Fetching product entity with ID: {}", id);
        return findProductById(id);
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productResponseDto) {
        logger.info("Creating product");
        logger.debug("Creating product with details: {}", productResponseDto);
        Product product = Product.builder()
                .name(productResponseDto.getName())
                .price(productResponseDto.getPrice())
                .description(productResponseDto.getDescription())
                .build();

        Product savedProduct = productRepository.save(product);

        ProductResponseDto responseDto = ProductResponseDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .build();

        logger.info("Created product successfully");
        logger.debug("Created product: {}", responseDto);
        return responseDto;
    }

    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDetails) {
        logger.info("Updating product");
        logger.debug("Updating product with ID: {} with details: {}", id, productDetails);

        Product oldProduct = findProductById(id);
        oldProduct.setName(productDetails.getName());
        oldProduct.setPrice(productDetails.getPrice());
        oldProduct.setDescription(productDetails.getDescription());

        Product savedProduct = productRepository.save(oldProduct);

        ProductResponseDto responseDto = ProductResponseDto.builder()
                .id(savedProduct.getId())
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .description(savedProduct.getDescription())
                .build();

        logger.info("Updated product successfully");
        logger.debug("Updated product: {}", responseDto);

        return responseDto;
    }

    @Override
    public boolean deleteProduct(Long id) {
        logger.info("Deleting product");
        logger.debug("Deleting product with ID: {}", id);

        Product product = findProductById(id);
        productRepository.delete(product);

        logger.info("Deleted product successfully");
        logger.debug("Deleted product with ID: {}", id);
        return true;
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));
    }
}
