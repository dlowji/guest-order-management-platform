package com.dlowji.simple.command.api.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ProgressOrderRequest {
    @NotNull(message = "Please enter order id")
    @NotEmpty(message = "Please enter valid order id")
    private String orderId;
    @Valid
    @NotNull(message = "Please enter progressOrderLineItemRequestList")
    private List<ProgressOrderLineItemRequest> progressOrderLineItemRequestList;
}
