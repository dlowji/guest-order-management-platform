package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.OrderLineItemStatus;
import lombok.Data;

@Data
public class ProgressOrderLineItemRequest {
    private Long id;
    private int quantity;
    private OrderLineItemStatus orderLineItemStatus;
}
