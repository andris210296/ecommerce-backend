package com.example.ecommerce.service.impl;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.exception.OrderItemNotCreatedException;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.service.CustomerOrderService;
import com.example.ecommerce.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderItemServiceImplTest {

    @InjectMocks
    private OrderItemServiceImpl orderItemServiceImpl;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerOrderService customerOrderService;

    @Test
    public void getAllOrderItemsTest() {

        when(orderItemRepository.findAll())
                .thenReturn(List.of(TestUtils.generateOrderItemId1()));

        List<OrderItemResponseDto> response = orderItemServiceImpl.getAllOrderItems();

        Assertions.assertEquals(1, response.size());
        Assertions.assertEquals(1, response.getFirst().getId());
        Assertions.assertEquals(1L, response.getFirst().getCustomerOrderId());
        Assertions.assertEquals(1L, response.getFirst().getProductId());
    }

    @Test
    public void getOrderItemByIdTest() {

        when(orderItemRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.generateOrderItemId1()));

        OrderItemResponseDto response = orderItemServiceImpl.getOrderItemById(1L);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(1L, response.getCustomerOrderId());
        Assertions.assertEquals(1L, response.getProductId());
    }

    @Test
    public void getOrderItemByIdExceptionTest() {

        when(orderItemRepository.findById(1L))
                .thenReturn(java.util.Optional.empty());

        Assertions.assertThrows(OrderItemNotCreatedException.class, () -> {
            orderItemServiceImpl.getOrderItemById(1L);
        });

    }

    @Test
    public void createOrderItemTest() {

        when(productService.getProductById(1L)).thenReturn(TestUtils.generateProductId1());

        when(customerOrderService.getOrderById(1L)).thenReturn(TestUtils.generateCustomerOrderId1());

        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(TestUtils.generateOrderItemId1());

        OrderItemResponseDto response = orderItemServiceImpl.createOrderItem(TestUtils.generateOrderItemRequestDtoId1());

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(1, response.getQuantity());
        Assertions.assertEquals(1L, response.getProductId());
        Assertions.assertEquals(1L, response.getCustomerOrderId());
    }

    @Test
    public void updateOrderItemTest() {

        Product productId2 = Product.builder()
                .id(2L)
                .name("Product")
                .price(BigDecimal.ONE)
                .build();

        when(productService.getProductById(2L)).thenReturn(productId2);

        CustomerOrder customerOrder = CustomerOrder.builder()
                .id(2L)
                .orderDate(LocalDate.now())
                .orderItems(List.of(OrderItem.builder()
                        .id(1L)
                        .quantity(1)
                        .product(productId2)
                        .build()))
                .total(BigDecimal.TEN)
                .build();

        when(customerOrderService.getOrderById(2L)).thenReturn(customerOrder);

        when(orderItemRepository.findById(1L)).thenReturn(Optional.of(TestUtils.generateOrderItemId1()));


        OrderItem orderItemId2 = OrderItem.builder()
                .id(1L)
                .quantity(2)
                .product(productId2)
                .customerOrder(customerOrder)
                .build();

        when(orderItemRepository.save(Mockito.any(OrderItem.class))).thenReturn(orderItemId2);

        OrderItemRequestDto newOrderItemDto = OrderItemRequestDto.builder()
                .productId(2L)
                .customerOrderId(2L)
                .quantity(2)
                .build();

        OrderItemResponseDto response = orderItemServiceImpl.updateOrderItem(1L, newOrderItemDto);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(2, response.getQuantity());
        Assertions.assertEquals(2L, response.getProductId());
        Assertions.assertEquals(2L, response.getCustomerOrderId());
    }

    @Test
    public void deleteOrderItemTest() {

        when(orderItemRepository.existsById(1L)).thenReturn(true);

        Assertions.assertTrue(orderItemServiceImpl.deleteOrderItem(1L));
    }

    @Test
    public void deleteOrderItemFalseTest() {

        when(orderItemRepository.existsById(1L)).thenReturn(false);

        Assertions.assertFalse(orderItemServiceImpl.deleteOrderItem(1L));
    }
}
