package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.Min;
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
    @Min(value = 0, message = "Quantity must be greater than 0")
    private Integer quantity;

    @NotNull(message = "Please enter note")
    @NotEmpty(message = "Please enter valid not")
    private String note;
}
