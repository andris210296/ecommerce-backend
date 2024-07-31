package com.example.ecommerce.service.impl;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.exception.OrderNotCreatedException;
import com.example.ecommerce.repository.CustomerOrderRepository;
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
public class CustomerOrderServiceImplTest {

    @InjectMocks
    private CustomerOrderServiceImpl customerOrderServiceImpl;

    @Mock
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void getAllOrdersTest() {

        when(customerOrderRepository.findAll())
                .thenReturn(List.of(TestUtils.generateCustomerOrderId1()));

        List<CustomerOrderResponseDto> response = customerOrderServiceImpl.getAllOrders();

        Assertions.assertEquals(1, response.size());
        CustomerOrderResponseDto orderResponse = response.getFirst();
        Assertions.assertEquals(1, orderResponse.getId());
        Assertions.assertEquals(LocalDate.now(), orderResponse.getOrderDate());
    }

    @Test
    public void getOrderByIdTest() {

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.generateCustomerOrderId1()));

        CustomerOrder response = customerOrderServiceImpl.getOrderById(1L);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(LocalDate.now(), response.getOrderDate());
        Assertions.assertEquals(BigDecimal.ZERO, response.getTotal());
    }

    @Test
    public void getOrderByIdDtoTest() {

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.generateCustomerOrderId1()));

        CustomerOrderResponseDto response = customerOrderServiceImpl.getOrderByIdDto(1L);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(LocalDate.now(), response.getOrderDate());
    }

    @Test
    public void getOrderByIdExceptionTest() {

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(OrderNotCreatedException.class, () -> {
            customerOrderServiceImpl.getOrderByIdDto(1L);
        });

    }

    @Test
    public void createOrderTest() {

        when(customerOrderRepository.save(Mockito.any(CustomerOrder.class)))
                .thenReturn(TestUtils.generateCustomerOrderId1());

        CustomerOrder response = customerOrderServiceImpl.createOrder(TestUtils.generateCustomerOrderRequestDtoId1());

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals(LocalDate.now(), response.getOrderDate());
        Assertions.assertEquals(BigDecimal.ZERO, response.getTotal());
    }

    @Test
    public void updateOrderTest() {

        LocalDate localDate = LocalDate.now();

        CustomerOrderRequestDto orderDto = CustomerOrderRequestDto.builder()
                .orderDate(localDate.plusDays(1))
                .build();

        CustomerOrder updatedOrder = TestUtils.generateCustomerOrderId1();
        updatedOrder.setOrderDate(localDate.plusDays(1));

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.generateCustomerOrderId1()));

        when(customerOrderRepository.save(Mockito.any(CustomerOrder.class)))
                .thenReturn(updatedOrder);

        CustomerOrder response = customerOrderServiceImpl.updateOrder(1L, orderDto);

        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals(localDate.plusDays(1), response.getOrderDate());
        Assertions.assertEquals(BigDecimal.ZERO, response.getTotal());
    }

    @Test
    public void deleteOrderTest() {

        when(customerOrderRepository.findById(1L))
                .thenReturn(Optional.of(TestUtils.generateCustomerOrderId1()));

        Assertions.assertTrue(customerOrderServiceImpl.deleteOrder(1L));

    }

}
