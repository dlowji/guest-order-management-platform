package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderLineItemRequest {
    @NotNull(message = "Please enter order line item id")
    private Long orderLineItemId;
    @NotNull(message = "Please enter quantity of ordered dish")
    @Min(value = 0, message = "Quantity must be greater than 0")
    private Integer quantity;
    @NotNull(message = "Please enter note")
    private String note;
    private boolean updateQuantity;
    private boolean updateNote;
}
