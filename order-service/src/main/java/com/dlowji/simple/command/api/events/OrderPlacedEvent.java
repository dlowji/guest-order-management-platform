package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPlacedEvent {
    private String orderId;
    private List<CustomOrderLineItemRequest> customOrderLineItemRequests;
}
