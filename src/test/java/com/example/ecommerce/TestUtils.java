package com.example.ecommerce;

import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.dto.ProductRequestDto;
import com.example.ecommerce.dto.ProductResponseDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TestUtils {

    public static Product generateProductId1() {
        return Product.builder()
                .id(1L)
                .name("Product 1")
                .price(BigDecimal.valueOf(100))
                .description("Description of Product 1")
                .build();
    }

    public static CustomerOrder generateCustomerOrderId1() {
        return CustomerOrder.builder()
                .id(1L)
                .orderDate(LocalDate.now())
                .total(BigDecimal.TEN)
                .build();
    }

    public static OrderItem generateOrderItemId1() {
        return OrderItem.builder()
                .id(1L)
                .quantity(1)
                .product(generateProductId1())
                .customerOrder(generateCustomerOrderId1())
                .build();
    }

    public static OrderItemResponseDto generateOrderItemResponseDtoId1() {
        return OrderItemResponseDto.builder()
                .id(1L)
                .quantity(1)
                .productId(1L)
                .customerOrderId(1L)
                .build();
    }

    public static OrderItemRequestDto generateOrderItemRequestDtoId1() {
        return OrderItemRequestDto.builder()
                .productId(1L)
                .customerOrderId(1L)
                .quantity(1)
                .build();
    }

    public static CustomerOrderRequestDto generateCustomerOrderRequestDtoId1() {
        return CustomerOrderRequestDto.builder()
                .orderDate(LocalDate.now())
                .build();
    }

    public static ProductResponseDto generateProductResponseDtoId1() {
        return ProductResponseDto.builder()
                .id(1L)
                .name("Product 1")
                .price(BigDecimal.valueOf(100))
                .build();
    }

    public static ProductRequestDto generateProductRequestDtoId1() {
        return ProductRequestDto.builder()
                .name("Product 1")
                .price(BigDecimal.valueOf(100))
                .description("Description of Product 1")
                .build();
    }

    public static CustomerOrderResponseDto generateCustomerOrderResponseDtoId1() {
        return CustomerOrderResponseDto.builder()
                .id(1L)
                .orderDate(LocalDate.now())
                .orderItemResponseDtoList(List.of(generateOrderItemResponseDtoId1()))
                .total(BigDecimal.TEN)
                .build();
    }


}
