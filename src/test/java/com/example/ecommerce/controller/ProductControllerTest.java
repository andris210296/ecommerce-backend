package com.example.ecommerce.controller;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ProductService productService;

    @Test
    public void getAllProductsTest() throws Exception{

        when(productService.getAllProducts())
                .thenReturn(List.of(TestUtils.generateProductResponseDtoId1()));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        List<ProductResponseDto> productResponseDtos = mapper.readValue(resultString, new TypeReference<>() {
        });

        Assertions.assertEquals(1L, productResponseDtos.getFirst().getId());
        Assertions.assertEquals("Product 1", productResponseDtos.getFirst().getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), productResponseDtos.getFirst().getPrice());
    }

    @Test
    public void getProductByIdTest() throws Exception{

        when(productService.getProductResponseById(1L))
                .thenReturn(TestUtils.generateProductResponseDtoId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        ProductResponseDto productResponseDto = mapper.readValue(resultString, new TypeReference<>() {
        });

        Assertions.assertEquals(1L, productResponseDto.getId());
        Assertions.assertEquals("Product 1", productResponseDto.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), productResponseDto.getPrice());
    }

    @Test
    public void createProductTest() throws Exception {

        when(productService.createProduct(TestUtils.generateProductRequestDtoId1()))
                .thenReturn(TestUtils.generateProductResponseDtoId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.generateProductRequestDtoId1())) // Ensure this is the request DTO
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        ProductResponseDto productResponseDto = mapper.readValue(resultString, new TypeReference<>() {
        });

        Assertions.assertEquals(1L, productResponseDto.getId());
        Assertions.assertEquals("Product 1", productResponseDto.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), productResponseDto.getPrice());
    }

    @Test
    public void updateProductTest() throws Exception {

        when(productService.updateProduct(1L, TestUtils.generateProductRequestDtoId1()))
                .thenReturn(TestUtils.generateProductResponseDtoId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.generateProductRequestDtoId1())) // Ensure this is the request DTO
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        ProductResponseDto productResponseDto = mapper.readValue(resultString, new TypeReference<>() {
        });

        Assertions.assertEquals(1L, productResponseDto.getId());
        Assertions.assertEquals("Product 1", productResponseDto.getName());
        Assertions.assertEquals(BigDecimal.valueOf(100), productResponseDto.getPrice());
    }

    @Test
    public void deleteProductTest() throws Exception{

        when(productService.deleteProduct(1L))
                .thenReturn(true);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteProductTestNotFound() throws Exception{

        when(productService.deleteProduct(1L))
                .thenReturn(false);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


}
