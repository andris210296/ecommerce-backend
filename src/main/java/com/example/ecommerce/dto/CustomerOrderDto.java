package com.example.ecommerce.dto;

import com.example.ecommerce.entity.CustomerOrder;

import java.util.Date;

public record CustomerOrderDto (Date orderDate, String status) {

    public CustomerOrder toCustomerOrder() {
        return new CustomerOrder(orderDate, status, null);
    }
}
