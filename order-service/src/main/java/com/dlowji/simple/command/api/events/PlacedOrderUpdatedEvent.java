package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.model.UpdateOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlacedOrderUpdatedEvent {
    private String orderId;
    List<UpdateOrderLineItemRequest> updateOrderLineItemRequestList;
}
