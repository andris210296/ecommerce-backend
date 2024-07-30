package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotCreatedException;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotCreatedException("The product with the provided ID does not exist"));
    }

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = Product.builder()
                .name(productDto.name())
                .price(productDto.price())
                .description(productDto.description())
                .build();

        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, ProductDto productDetails) {
        Product oldProduct = getProductById(id);

        oldProduct.setName(productDetails.name());
        oldProduct.setPrice(productDetails.price());
        oldProduct.setDescription(productDetails.description());

        return productRepository.save(oldProduct);
    }

    @Override
    public boolean deleteProduct(Long id) {
        Product product = getProductById(id);
        productRepository.delete(product);
        return true;
    }
}
