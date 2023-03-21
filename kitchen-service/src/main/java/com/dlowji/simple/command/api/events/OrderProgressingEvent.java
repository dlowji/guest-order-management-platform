package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.model.MarkDoneOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderProgressingEvent {
    private String orderId;
    private List<MarkDoneOrderLineItemRequest> progressOrderLineItemRequestList;
}
