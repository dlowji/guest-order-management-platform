package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderLineItemRequest {
    private Long orderLineItemId;
    @NotNull(message = "Please enter dish id")
    @NotEmpty(message = "Please enter a valid dish id")
    private String dishId;
    private BigDecimal price;
    @NotNull(message = "Please enter quantity")
    private Integer quantity;
    @NotNull(message = "Please enter note")
    private String note;
    private boolean create;
    private boolean updateQuantity;
    private boolean updateNote;
}
