package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
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
    public List<OrderItemResponseDto> getAllOrderItems() {

        List<OrderItem> orderItems = orderItemRepository.findAll();

        return orderItems.stream()
                .map(orderItem -> OrderItemResponseDto.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProduct().getId())
                        .customerOrderId(orderItem.getCustomerOrder().getId())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long id) {

        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotCreatedException("The order item with the provided ID does not exist"));

        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .customerOrderId(orderItem.getCustomerOrder().getId())
                .quantity(orderItem.getQuantity())
                .build();
    }

    @Override
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto orderItemResponseDto) {
        Product product = productService.getProductById(orderItemResponseDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(orderItemResponseDto.getCustomerOrderId());

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .customerOrder(customerOrder)
                .quantity(orderItemResponseDto.getQuantity())
                .build();

        OrderItem orderItemSaved = orderItemRepository.save(orderItem);

        return OrderItemResponseDto.builder()
                .id(orderItemSaved.getId())
                .productId(orderItemSaved.getProduct().getId())
                .customerOrderId(orderItemSaved.getCustomerOrder().getId())
                .quantity(orderItemSaved.getQuantity())
                .build();
    }

    @Override
    public OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto newOrderItemResponseDto) {
        OrderItem oldOrderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotCreatedException("The order item with the provided ID does not exist"));
        Product product = productService.getProductById(newOrderItemResponseDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(newOrderItemResponseDto.getCustomerOrderId());

        oldOrderItem.setQuantity(newOrderItemResponseDto.getQuantity());
        oldOrderItem.setProduct(product);
        oldOrderItem.setCustomerOrder(customerOrder);

        OrderItem savedOrderItem = orderItemRepository.save(oldOrderItem);

        return OrderItemResponseDto.builder()
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