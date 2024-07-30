package com.example.ecommerce.controller;

import com.example.ecommerce.dto.OrderItemRequestDto;
import com.example.ecommerce.dto.OrderItemResponseDto;
import com.example.ecommerce.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@Tag(name = "Order Item Management", description = "APIs for managing order items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items")
    public ResponseEntity<List<OrderItemResponseDto>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order item by ID", description = "Retrieve an order item by its ID")
    public ResponseEntity<OrderItemResponseDto> getOrderItemById(@PathVariable Long id) {
        return ResponseEntity.ok(orderItemService.getOrderItemById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new order item", description = "Create a new order item with the provided details")
    public ResponseEntity<OrderItemResponseDto> createOrderItem(@RequestBody @Valid OrderItemRequestDto orderItemResponseDto) {
        return ResponseEntity.ok(orderItemService.createOrderItem(orderItemResponseDto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order item", description = "Update an existing order item with the provided details")
    public ResponseEntity<OrderItemResponseDto> updateOrderItem(@PathVariable Long id, @RequestBody @Valid OrderItemRequestDto orderItemResponseDto) {
        return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItemResponseDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item", description = "Delete an order item by its ID")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        boolean isDeleted = orderItemService.deleteOrderItem(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}