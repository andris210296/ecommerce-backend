package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItemDto> getAllOrderItems();
    OrderItemDto getOrderItemById(Long id);
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);
    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);
    boolean deleteOrderItem(Long id);
}
