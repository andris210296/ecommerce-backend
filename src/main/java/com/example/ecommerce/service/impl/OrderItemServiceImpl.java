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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemServiceImpl.class);

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerOrderService customerOrderService;

    @Override
    public List<OrderItemResponseDto> getAllOrderItems() {
        logger.info("Fetching all order items");

        List<OrderItemResponseDto> orderItems = orderItemRepository.findAll().stream()
                .map(this::mapToOrderItemResponseDto)
                .collect(Collectors.toList());

        logger.debug("Fetched {} order items", orderItems.size());
        logger.info("Fetched order items successfully");
        return orderItems;
    }

    @Override
    public OrderItemResponseDto getOrderItemById(Long id) {
        logger.info("Fetching order item by ID");
        logger.debug("Fetching order item with ID: {}", id);

        OrderItem orderItem = findOrderItemById(id);
        OrderItemResponseDto responseDto = mapToOrderItemResponseDto(orderItem);

        logger.debug("Fetched order item: {}", responseDto);
        logger.info("Fetched order item by id successfully");
        return responseDto;
    }

    @Override
    public OrderItemResponseDto createOrderItem(OrderItemRequestDto orderItemRequestDto) {
        logger.info("Creating order item");
        logger.debug("Creating order item with details: {}", orderItemRequestDto);

        Product product = productService.getProductById(orderItemRequestDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(orderItemRequestDto.getCustomerOrderId());

        OrderItem orderItem = OrderItem.builder()
                .product(product)
                .customerOrder(customerOrder)
                .quantity(orderItemRequestDto.getQuantity())
                .build();

        OrderItem orderItemSaved = orderItemRepository.save(orderItem);
        OrderItemResponseDto responseDto = mapToOrderItemResponseDto(orderItemSaved);

        logger.info("Created order item successfully");
        logger.debug("Created order item: {}", responseDto);
        return responseDto;
    }

    @Override
    public OrderItemResponseDto updateOrderItem(Long id, OrderItemRequestDto newOrderItemRequestDto) {
        logger.info("Updating order item");
        logger.debug("Updating order item with ID: {} with details: {}", id, newOrderItemRequestDto);

        OrderItem oldOrderItem = findOrderItemById(id);
        Product product = productService.getProductById(newOrderItemRequestDto.getProductId());
        CustomerOrder customerOrder = customerOrderService.getOrderById(newOrderItemRequestDto.getCustomerOrderId());

        oldOrderItem.setQuantity(newOrderItemRequestDto.getQuantity());
        oldOrderItem.setProduct(product);
        oldOrderItem.setCustomerOrder(customerOrder);

        OrderItem savedOrderItem = orderItemRepository.save(oldOrderItem);
        OrderItemResponseDto responseDto = mapToOrderItemResponseDto(savedOrderItem);

        logger.info("Updated order item successfully");
        logger.debug("Updated order item: {}", responseDto);
        return responseDto;
    }

    @Override
    public boolean deleteOrderItem(Long id) {
        logger.info("Deleting order item");
        logger.debug("Deleting order item with ID: {}", id);

        if (orderItemRepository.existsById(id)) {
            orderItemRepository.deleteById(id);
            logger.info("Deleted order item successfully");
            logger.debug("Deleted order item with ID: {}", id);
            return true;
        }

        logger.warn("Order item with ID: {} does not exist", id);
        return false;
    }

    private OrderItem findOrderItemById(Long id) {
        logger.info("Finding order item by ID");
        logger.debug("Finding order item with ID: {}", id);

        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotCreatedException("The order item with the provided ID does not exist"));
    }

    private OrderItemResponseDto mapToOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProduct().getId())
                .customerOrderId(orderItem.getCustomerOrder().getId())
                .quantity(orderItem.getQuantity())
                .build();
    }
}