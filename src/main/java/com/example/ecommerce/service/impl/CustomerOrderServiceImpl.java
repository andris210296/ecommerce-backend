package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.repository.CustomerOrderRepository;
import com.example.ecommerce.service.CustomerOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrder> getAllOrders() {
        return customerOrderRepository.findAll();
    }

    @Override
    public Optional<CustomerOrder> getOrderById(Long id) {
        return customerOrderRepository.findById(id);
    }

    @Override
    public CustomerOrder createOrder(CustomerOrderDto orderDto) {
        CustomerOrder order = orderDto.toCustomerOrder();
        return customerOrderRepository.save(order);
    }

    @Override
    public Optional<CustomerOrder> updateOrder(Long id, CustomerOrderDto orderDetails) {
        return customerOrderRepository.findById(id).map(order -> {
            order.setOrderDate(orderDetails.orderDate());
            order.setOrderItems(orderDetails.toCustomerOrder().getOrderItems());
            return customerOrderRepository.save(order);
        });
    }

    @Override
    public boolean deleteOrder(Long id) {
        return customerOrderRepository.findById(id).map(order -> {
            customerOrderRepository.delete(order);
            return true;
        }).orElse(false);
    }

}