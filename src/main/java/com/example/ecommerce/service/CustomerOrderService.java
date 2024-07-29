package com.example.ecommerce.service;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.entity.CustomerOrder;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderService {
    List<CustomerOrder> getAllOrders();

    Optional<CustomerOrder> getOrderById(Long id);

    CustomerOrder createOrder(CustomerOrderDto order);

    Optional<CustomerOrder> updateOrder(Long id, CustomerOrderDto order);

    boolean deleteOrder(Long id);
}
