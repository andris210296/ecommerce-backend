package com.example.ecommerce.controller;

import com.example.ecommerce.dto.CustomerOrderRequestDto;
import com.example.ecommerce.dto.CustomerOrderResponseDto;
import com.example.ecommerce.entity.CustomerOrder;
import com.example.ecommerce.service.CustomerOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Order Management", description = "APIs for managing orders")
public class CustomerOrderController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieve a list of all orders")
    public ResponseEntity<List<CustomerOrderResponseDto>> getAllOrders() {
        return ResponseEntity.ok(customerOrderService.getAllOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID", description = "Retrieve an order by its ID")
    public ResponseEntity<CustomerOrderResponseDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(customerOrderService.getOrderByIdDto(id));
    }

    @PostMapping
    @Operation(summary = "Create a new order", description = "Create a new order with the provided details")
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody @Valid CustomerOrderRequestDto orderDto) {
        return ResponseEntity.ok(customerOrderService.createOrder(orderDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order", description = "Update an existing order with the provided details")
    public ResponseEntity<CustomerOrder> updateOrder(@PathVariable Long id, @RequestBody @Valid CustomerOrderRequestDto orderDetails) {
        return ResponseEntity.ok(customerOrderService.updateOrder(id, orderDetails));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order", description = "Delete an order by its ID")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (customerOrderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}