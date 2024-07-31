package com.example.ecommerce.controller;

import com.example.ecommerce.TestUtils;
import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.service.OrderItemService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private OrderItemService orderItemService;

    @Test
    public void getAllOrderItemsTest() throws Exception {

        when(orderItemService.getAllOrderItems())
                .thenReturn(List.of(TestUtils.generateOrderItemResponseDtoId1()));

        ResultActions result = mockMvc.perform(get("/api/order-items")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        List<OrderItemResponseDto> orderItemResponseDtoList = mapper.readValue(resultString, new TypeReference<List<OrderItemResponseDto>>() {});

        Assertions.assertEquals(1L, orderItemResponseDtoList.getFirst().getCustomerOrderId());
        Assertions.assertEquals(1L, orderItemResponseDtoList.getFirst().getProductId());
    }

    @Test
    public void getOrderItemByIdTest() throws Exception {

        when(orderItemService.getOrderItemById(1L))
                .thenReturn(TestUtils.generateOrderItemResponseDtoId1());

        ResultActions result = mockMvc.perform(get("/api/order-items/1")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        OrderItemResponseDto orderItemResponseDto = mapper.readValue(resultString, OrderItemResponseDto.class);

        Assertions.assertEquals(1L, orderItemResponseDto.getCustomerOrderId());
        Assertions.assertEquals(1L, orderItemResponseDto.getProductId());
    }

    @Test
    public void createOrderItemTest() throws Exception {


        when(orderItemService.createOrderItem(TestUtils.generateOrderItemRequestDtoId1()))
                .thenReturn(TestUtils.generateOrderItemResponseDtoId1());

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/order-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(TestUtils.generateOrderItemRequestDtoId1()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        OrderItemResponseDto orderItemResponseDtoResult = mapper.readValue(resultString, OrderItemResponseDto.class);

        Assertions.assertEquals(1L, orderItemResponseDtoResult.getCustomerOrderId());
        Assertions.assertEquals(1L, orderItemResponseDtoResult.getProductId());
    }

    @Test
    public void updateOrderItemTest() throws Exception {
        OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto
                .builder()
                .customerOrderId(2L)
                .productId(2L)
                .quantity(2)
                .build();

        OrderItemRequestDto orderItemRequestDto = OrderItemRequestDto
                .builder()
                .customerOrderId(2L)
                .productId(2L)
                .quantity(2)
                .build();

        when(orderItemService.updateOrderItem(1L, orderItemRequestDto))
                .thenReturn(orderItemResponseDto);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/order-items/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(orderItemRequestDto))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        OrderItemResponseDto orderItemResponseDtoResult = mapper.readValue(resultString, OrderItemResponseDto.class);

        Assertions.assertEquals(2L, orderItemResponseDtoResult.getCustomerOrderId());
        Assertions.assertEquals(2L, orderItemResponseDtoResult.getProductId());
    }

    @Test
    public void deleteOrderItemTest() throws Exception {

        when(orderItemService.deleteOrderItem(1L))
                .thenReturn(true);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/order-items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteOrderItemTestNotFound() throws Exception {

        when(orderItemService.deleteOrderItem(1L))
                .thenReturn(false);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/order-items/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
