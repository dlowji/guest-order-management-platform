package com.dlowji.simple.command.api.commands;

import com.dlowji.simple.command.api.model.ProgressOrderLineItemRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgressOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private List<ProgressOrderLineItemRequest> progressOrderLineItemRequestList;
}
