package com.dlowji.simple.command.api.model;

import com.dlowji.simple.enums.OrderLineItemStatus;
import lombok.Data;

@Data
public class MarkDoneOrderLineItemRequest {
    private Long id;
    private OrderLineItemStatus orderLineItemStatus;
}
