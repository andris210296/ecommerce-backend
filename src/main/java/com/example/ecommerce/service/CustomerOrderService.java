package com.example.ecommerce.service;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.entity.CustomerOrder;

import java.util.List;

public interface CustomerOrderService {
    List<CustomerOrderDto> getAllOrders();

    CustomerOrderDto getOrderByIdDto(Long id);

    CustomerOrder getOrderById(Long id);

    CustomerOrder createOrder(CustomerOrderDto order);

    CustomerOrder updateOrder(Long id, CustomerOrderDto order);

    boolean deleteOrder(Long id);
}
