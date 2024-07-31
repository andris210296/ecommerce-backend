package com.example.ecommerce.service.impl;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.ProductNotCreatedException;
import com.example.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    @Mock
    private ProductRepository productRepository;

    @Test
    public void getAllOrdersTest() {

        when(productRepository.findAll())
                .thenReturn(List.of(TestUtils.generateProductId1()));

        List<ProductResponseDto> response = productServiceImpl.getAllProducts();

        Assertions.assertEquals(1, response.size());
        ProductResponseDto productResponse = response.getFirst();
        Assertions.assertEquals(1L, productResponse.getId());
        Assertions.assertEquals("Product 1", productResponse.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), productResponse.getPrice());
        Assertions.assertEquals("Description of Product 1", productResponse.getDescription());
    }

    @Test
    public void getProductByIdTest() {

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(TestUtils.generateProductId1()));

        ProductResponseDto response = productServiceImpl.getProductResponseById(1L);

        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals("Product 1", response.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), response.getPrice());
        Assertions.assertEquals("Description of Product 1", response.getDescription());
    }

    @Test
    public void getProductByIdExceptionTest() {

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(ProductNotCreatedException.class, () -> {
            productServiceImpl.getProductResponseById(1L);
        });
    }

    @Test
    public void createProductTest() {

        Product productToSave = TestUtils.generateProductId1();
        productToSave.setId(null);

        doReturn(TestUtils.generateProductId1()).when(productRepository).save(productToSave);

        ProductResponseDto response = productServiceImpl.createProduct(TestUtils.generateProductRequestDtoId1());

        Assertions.assertEquals("Product 1", response.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), response.getPrice());
        Assertions.assertEquals("Description of Product 1", response.getDescription());
    }

    @Test
    public void updateProductTest() {

        Product productToUpdate = TestUtils.generateProductId1();

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(productToUpdate));

        Product productToSave = Product.builder()
                .id(1L)
                .name("Updated Product 1")
                .price(BigDecimal.valueOf(200))
                .description("Updated Description of Product 1")
                .build();


        doReturn(productToSave).when(productRepository).save(productToUpdate);

        ProductResponseDto response = productServiceImpl.updateProduct(1L, TestUtils.generateProductRequestDtoId1());

        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals("Updated Product 1", response.getName());
        Assertions.assertEquals(BigDecimal.valueOf(200), response.getPrice());
        Assertions.assertEquals("Updated Description of Product 1", response.getDescription());
    }

    @Test
    public void deleteProductTest() {

        when(productRepository.findById(1L))
                .thenReturn(java.util.Optional.of(TestUtils.generateProductId1()));

        Assertions.assertTrue(productServiceImpl.deleteProduct(1L));
    }

}
