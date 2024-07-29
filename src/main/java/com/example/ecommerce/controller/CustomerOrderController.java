package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CustomerOrderDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.service.impl.CustomerOrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderServiceImpl customerOrderServiceImpl;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    public List<CustomerOrder> getAllOrders() {
        return customerOrderServiceImpl.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long id) {
        return customerOrderServiceImpl.getOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrderDto orderDto) {
        return ResponseEntity.ok(customerOrderServiceImpl.createOrder(orderDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order", description = "Update an existing order with the provided details")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Long id, @RequestBody CustomerOrderDto orderDetails) {
        return customerOrderServiceImpl.updateOrder(id, orderDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (customerOrderServiceImpl.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}