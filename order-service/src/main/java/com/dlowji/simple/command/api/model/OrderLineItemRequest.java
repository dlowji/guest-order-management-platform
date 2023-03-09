package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemRequest {
    @NotNull(message = "Please enter dish id")
    @NotEmpty(message = "Please enter a valid dish id")
    private String dishId;
    @NotNull(message = "Please enter quantity of ordered dish")
    @NotEmpty(message = "Please enter a valid quantity of ordered dish")
    private Integer quantity;
}
