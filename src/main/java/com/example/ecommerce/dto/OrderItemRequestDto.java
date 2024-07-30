package com.example.ecommerce.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemRequestDto {

    @NotNull(message = "Product ID is required")
    @Range(min = 1)
    private Long productId;

    @NotNull(message = "Customer Order ID is required")
    @Range(min = 1)
    private Long customerOrderId;

    @NotNull(message = "Quantity is required")
    @Range(min = 1)
    private int quantity;
}
