package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.model.ProgressOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderProgressedEvent {
    private String orderId;
    private List<ProgressOrderLineItemRequest> progressOrderLineItemRequestList;
}
