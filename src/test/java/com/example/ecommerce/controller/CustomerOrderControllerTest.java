package com.example.ecommerce.controller;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.service.CustomerOrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerOrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CustomerOrderService customerOrderService;

    @Test
    public void getAllOrdersTest() throws Exception {

        when(customerOrderService.getAllOrders())
                .thenReturn(List.of(TestUtils.generateCustomerOrderResponseDtoId1()));

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        List<CustomerOrderResponseDto> customerOrderResponseDtos = mapper.readValue(resultString, new TypeReference<>() {
        });

        CustomerOrderResponseDto response = customerOrderResponseDtos.getFirst();

        Assertions.assertEquals(1L, response.getId());
        Assertions.assertEquals(LocalDate.now(), response.getOrderDate());
        Assertions.assertEquals(BigDecimal.TEN, response.getTotal());
        Assertions.assertEquals(1, response.getOrderItemResponseDtoList().size());
        Assertions.assertEquals(1L, response.getOrderItemResponseDtoList().getFirst().getId());
    }

    @Test
    public void getOrderByIdTest() throws Exception {

        when(customerOrderService.getOrderByIdDto(1L))
                .thenReturn(TestUtils.generateCustomerOrderResponseDtoId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        CustomerOrderResponseDto customerOrderResponseDto = mapper.readValue(resultString, CustomerOrderResponseDto.class);

        Assertions.assertEquals(1L, customerOrderResponseDto.getId());
        Assertions.assertEquals(LocalDate.now(), customerOrderResponseDto.getOrderDate());
        Assertions.assertEquals(BigDecimal.TEN, customerOrderResponseDto.getTotal());
        Assertions.assertEquals(1, customerOrderResponseDto.getOrderItemResponseDtoList().size());
        Assertions.assertEquals(1L, customerOrderResponseDto.getOrderItemResponseDtoList().getFirst().getId());
    }

    @Test
    public void createOrderTest() throws Exception {

        LocalDate localDate = LocalDate.now();

        CustomerOrderRequestDto customerOrderRequestDto = CustomerOrderRequestDto.builder().orderDate(localDate).build();

        when(customerOrderService.createOrder(customerOrderRequestDto))
                .thenReturn(TestUtils.generateCustomerOrderId1());

        ResultActions result = mockMvc.perform(post("/api/orders")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customerOrderRequestDto))
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        CustomerOrder customerOrder = mapper.readValue(resultString, CustomerOrder.class);

        Assertions.assertEquals(1L, customerOrder.getId());
        Assertions.assertEquals(localDate, customerOrder.getOrderDate());
        Assertions.assertEquals(BigDecimal.ZERO, customerOrder.getTotal());
    }

    @Test
    public void updateOrderTest() throws Exception {

        LocalDate localDate = LocalDate.now();

        CustomerOrderRequestDto customerOrderRequestDto = CustomerOrderRequestDto.builder().orderDate(localDate.plusDays(1)).build();

        when(customerOrderService.updateOrder(1L, customerOrderRequestDto))
                .thenReturn(TestUtils.generateCustomerOrderId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(customerOrderRequestDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        CustomerOrder customerOrder = mapper.readValue(resultString, CustomerOrder.class);

        Assertions.assertEquals(1L, customerOrder.getId());
        Assertions.assertEquals(localDate, customerOrder.getOrderDate());
        Assertions.assertEquals(BigDecimal.ZERO, customerOrder.getTotal());
    }

    @Test
    public void deleteOrderTest() throws Exception {

        when(customerOrderService.deleteOrder(1L))
                .thenReturn(true);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Assertions.assertTrue(result.andReturn().getResponse().getContentAsString().isEmpty());
    }

    @Test
    public void deleteOrderTestNotFound() throws Exception {

        when(customerOrderService.deleteOrder(1L))
                .thenReturn(false);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        Assertions.assertTrue(result.andReturn().getResponse().getContentAsString().isEmpty());
    }
}
