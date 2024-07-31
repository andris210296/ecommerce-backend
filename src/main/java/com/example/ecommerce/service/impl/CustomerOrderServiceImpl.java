package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.exception.OrderNotCreatedException;
import com.example.ecommerce.repository.CustomerOrderRepository;
import com.example.ecommerce.service.CustomerOrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerOrderServiceImpl implements CustomerOrderService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerOrderServiceImpl.class);

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Override
    public List<CustomerOrderResponseDto> getAllOrders() {
        logger.info("Fetching all customer orders");

        List<CustomerOrderResponseDto> orders = customerOrderRepository.findAll().stream()
                .map(order -> CustomerOrderResponseDto.builder()
                        .id(order.getId())
                        .orderDate(order.getOrderDate())
                        .total(order.getTotal())
                        .orderItemResponseDtoList(order.getOrderItems().stream()
                                .map(orderItem -> OrderItemResponseDto.builder()
                                        .id(orderItem.getId())
                                        .productId(orderItem.getProduct().getId())
                                        .customerOrderId(orderItem.getCustomerOrder().getId())
                                        .quantity(orderItem.getQuantity())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());

        logger.debug("Fetched {} customer orders", orders.size());
        logger.info("Fetched customer orders successfully");
        return orders;
    }

    @Override
    public CustomerOrderResponseDto getOrderByIdDto(Long id) {
        logger.info("Fetching customer order by ID");
        logger.debug("Fetching customer order with ID: {}", id);

        CustomerOrder order = findCustomerOrderById(id);
        CustomerOrderResponseDto responseDto = CustomerOrderResponseDto.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .total(order.getTotal())
                .orderItemResponseDtoList(order.getOrderItems().stream()
                        .map(orderItem -> OrderItemResponseDto.builder()
                                .id(orderItem.getId())
                                .productId(orderItem.getProduct().getId())
                                .customerOrderId(orderItem.getCustomerOrder().getId())
                                .quantity(orderItem.getQuantity())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        logger.debug("Fetched customer order: {}", responseDto);
        logger.info("Fetched customer order by id successfully");
        return responseDto;
    }

    @Override
    public CustomerOrder getOrderById(Long id) {
        logger.info("Fetching customer order entity by ID");
        logger.debug("Fetching customer order entity with ID: {}", id);

        return findCustomerOrderById(id);
    }

    @Override
    public CustomerOrder createOrder(CustomerOrderRequestDto orderDto) {
        logger.info("Creating customer order");
        logger.debug("Creating customer order with details: {}", orderDto);

        LocalDate orderDate = orderDto.getOrderDate() != null ? orderDto.getOrderDate() : LocalDate.now();
        CustomerOrder order = CustomerOrder.builder()
                .orderDate(orderDate)
                .build();
        CustomerOrder savedOrder = customerOrderRepository.save(order);

        logger.info("Created customer order successfully");
        logger.debug("Created customer order: {}", savedOrder);

        return savedOrder;
    }

    @Override
    public CustomerOrder updateOrder(Long id, CustomerOrderRequestDto orderDetails) {
        logger.info("Updating customer order");
        logger.debug("Updating customer order with ID: {} with details: {}", id, orderDetails);

        CustomerOrder oldCustomerOrder = findCustomerOrderById(id);
        oldCustomerOrder.setOrderDate(orderDetails.getOrderDate());
        CustomerOrder updatedOrder = customerOrderRepository.save(oldCustomerOrder);

        logger.info("Updated customer order successfully");
        logger.debug("Updated customer order: {}", updatedOrder);

        return updatedOrder;
    }

    @Override
    public boolean deleteOrder(Long id) {
        logger.info("Deleting customer order");
        logger.debug("Deleting customer order with ID: {}", id);

        CustomerOrder customerOrder = findCustomerOrderById(id);
        customerOrderRepository.delete(customerOrder);

        logger.info("Deleted customer order successfully");
        logger.debug("Deleted customer order with ID: {}", id);

        return true;
    }

    private CustomerOrder findCustomerOrderById(Long id) {
        logger.info("Finding customer order by ID");
        logger.debug("Finding customer order with ID: {}", id);

        return customerOrderRepository.findById(id)
                .orElseThrow(() -> new OrderNotCreatedException("The order with the provided ID does not exist"));
    }
}