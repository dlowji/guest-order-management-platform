package com.dlowji.simple.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarkOrderLineItemDoneCommand {
    @TargetAggregateIdentifier
    private String orderId;

    private Long orderLineItemId;
}
