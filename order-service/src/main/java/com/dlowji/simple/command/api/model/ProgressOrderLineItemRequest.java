package com.dlowji.simple.command.api.model;

import com.dlowji.simple.enums.OrderLineItemStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressOrderLineItemRequest {
    @NotNull(message = "Please enter order line item id")
    private Long id;
    @NotNull(message = "Please enter quantity")
    private int quantity;
    @NotNull(message = "Please enter order line item status")
    private OrderLineItemStatus orderLineItemStatus;
}
