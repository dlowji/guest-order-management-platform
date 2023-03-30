package com.dlowji.simple.commands;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@Builder
public class MarkOrderLineItemDoneCommandVersion2 {
    @TargetAggregateIdentifier
    private String orderId;

    private String test;
    private List<Long> test2;
}
