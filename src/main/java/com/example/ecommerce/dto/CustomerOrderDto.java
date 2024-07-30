package com.example.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderDto {

    private Long id;
    private LocalDate orderDate;
    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();
    private BigDecimal total;

}
