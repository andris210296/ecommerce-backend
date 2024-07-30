package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponseDto> getAllOrderItems();
    OrderItemResponseDto getOrderItemById(Long id);
    OrderItemResponseDto createOrderItem(OrderItemRequestDto orderItemResponseDto);
    OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto orderItemResponseDto);
    boolean deleteOrderItem(Long id);
}
