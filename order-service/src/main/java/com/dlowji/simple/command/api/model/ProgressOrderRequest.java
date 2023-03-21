package com.dlowji.simple.command.api.model;

import lombok.Data;

import java.util.List;

@Data
public class ProgressOrderRequest {
    private String orderId;
    private List<ProgressOrderLineItemRequest> progressOrderLineItemRequestList;
}
