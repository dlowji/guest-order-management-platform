package com.dlowji.simple.command.api.commands;

import com.dlowji.simple.command.api.model.UpdateOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePlacedOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private List<UpdateOrderLineItemRequest> updateOrderLineItemRequestList;
}

