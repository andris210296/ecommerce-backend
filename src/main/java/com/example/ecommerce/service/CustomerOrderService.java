package com.example.ecommerce.service;

import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.entity.CustomerOrder;

import java.util.List;

public interface CustomerOrderService {
    List<CustomerOrderResponseDto> getAllOrders();

    CustomerOrderResponseDto getOrderByIdDto(Long id);

    CustomerOrder getOrderById(Long id);

    CustomerOrder createOrder(CustomerOrderRequestDto order);

    CustomerOrder updateOrder(Long id, CustomerOrderRequestDto order);

    boolean deleteOrder(Long id);
}
