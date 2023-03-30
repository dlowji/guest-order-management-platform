package com.dlowji.simple.commands;

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
public class MarkOrderLineItemsDoneCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private List<Long> orderLineItemIds;
}
