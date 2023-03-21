package com.dlowji.simple.command.api.model;

import lombok.Data;

import java.util.List;

@Data
public class MarkDoneOrderRequest {
    private String orderId;
    private List<MarkDoneOrderLineItemRequest> markDoneOrderLineItemRequests;
}
