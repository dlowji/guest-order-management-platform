package com.dlowji.simple.command.api.model;

import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private String orderId;
    List<OrderLineItemRequest> orderLineItemRequestList;
}
