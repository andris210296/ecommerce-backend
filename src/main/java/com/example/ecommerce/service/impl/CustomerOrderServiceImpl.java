package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.exception.OrderNotCreatedException;
import com.example.ecommerce.repository.CustomerOrderRepository;
import com.example.ecommerce.service.CustomerOrderService;
import com.example.ecommerce.service.OrderItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrderDto> getAllOrders() {

        List<CustomerOrder> orders = customerOrderRepository.findAll();

        return orders.stream()
                .map(order -> CustomerOrderDto.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .total(order.getTotal())
                        .orderItemDtoList(order.getOrderItems().stream()
                                .map(orderItem -> OrderItemDto.builder()
                                        .id(orderItem.getId())
                                        .productId(orderItem.getProduct().getId())
                                        .customerOrderId(orderItem.getCustomerOrder().getId())
                                        .quantity(orderItem.getQuantity())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CustomerOrderDto getOrderByIdDto(Long id) {

        CustomerOrder order = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotCreatedException("The order with the provided ID does not exist"));

        return CustomerOrderDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .orderItemDtoList(order.getOrderItems().stream()
                        .map(orderItem -> OrderItemDto.builder()
                                .id(orderItem.getId())
                                .productId(orderItem.getProduct().getId())
                                .customerOrderId(orderItem.getCustomerOrder().getId())
                                .quantity(orderItem.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public CustomerOrder getOrderById(Long id) {
        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotCreatedException("The order with the provided ID does not exist"));
    }

    @Override
    public CustomerOrder createOrder(CustomerOrderDto orderDto) {
        CustomerOrder order = CustomerOrder.builder()
                .orderDate(LocalDate.now())
                .build();
        return customerOrderRepository.save(order);
    }

    @Override
    public CustomerOrder updateOrder(Long id, CustomerOrderDto orderDetails) {

        CustomerOrder oldCustomerOrder = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotCreatedException("The order with the provided ID does not exist"));

        oldCustomerOrder.setOrderDate(orderDetails.getOrderDate());
        return customerOrderRepository.save(oldCustomerOrder);
    }

    @Override
    public boolean deleteOrder(Long id) {
        CustomerOrder customerOrder = customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotCreatedException("The order with the provided ID does not exist"));
        customerOrderRepository.delete(customerOrder);
        return true;
    }

}