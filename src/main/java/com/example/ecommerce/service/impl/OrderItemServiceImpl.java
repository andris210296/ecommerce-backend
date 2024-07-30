package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.exception.OrderItemNotCreatedException;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.service.CustomerOrderService;
import com.example.ecommerce.service.OrderItemService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Override
    public List<OrderItemDto> getAllOrderItems() {

        List<OrderItem> orderItems = orderItemRepository.findAll();

        return orderItems.stream()
                .map(orderItem -> OrderItemDto.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProduct().getId())
                        .customerOrderId(orderItem.getCustomerOrder().getId())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotCreatedException("The order item with the provided ID does not exist"));

        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .customerOrderId(orderItem.getCustomerOrder().getId())
                .quantity(orderItem.getQuantity())
                .build();
    }

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        Product product = productService.getProductById(orderItemDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(orderItemDto.getCustomerOrderId());

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .customerOrder(customerOrder)
                .quantity(orderItemDto.getQuantity())
                .build();

        OrderItem orderItemSaved = orderItemRepository.save(orderItem);

        return OrderItemDto.builder()
                .id(orderItemSaved.getId())
                .productId(orderItemSaved.getProduct().getId())
                .customerOrderId(orderItemSaved.getCustomerOrder().getId())
                .quantity(orderItemSaved.getQuantity())
                .build();
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto newOrderItemDto) {
        OrderItem oldOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotCreatedException("The order item with the provided ID does not exist"));
        Product product = productService.getProductById(newOrderItemDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(newOrderItemDto.getCustomerOrderId());

        oldOrderItem.setQuantity(newOrderItemDto.getQuantity());
        oldOrderItem.setProduct(product);
        oldOrderItem.setCustomerOrder(customerOrder);

        OrderItem savedOrderItem = orderItemRepository.save(oldOrderItem);

        return OrderItemDto.builder()
                .id(savedOrderItem.getId())
                .productId(savedOrderItem.getProduct().getId())
                .customerOrderId(savedOrderItem.getCustomerOrder().getId())
                .quantity(savedOrderItem.getQuantity())
                .build();
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            return true;
        }
        return false;
    }

}