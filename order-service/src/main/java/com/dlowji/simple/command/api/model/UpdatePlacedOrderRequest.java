package com.dlowji.simple.command.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UpdatePlacedOrderRequest {
    @NotNull(message = "Please enter order id")
    @NotEmpty(message = "Please enter a valid order id")
    private String orderId;
    @Valid
    @NotNull(message = "list of order line item must not be null")
    List<UpdateOrderLineItemRequest> updateOrderLineItemRequests;
}
