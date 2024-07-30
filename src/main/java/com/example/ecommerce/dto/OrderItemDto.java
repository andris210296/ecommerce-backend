package com.example.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    private Long id;
    private Long productId;
    private Long customerOrderId;
    private int quantity;
}
