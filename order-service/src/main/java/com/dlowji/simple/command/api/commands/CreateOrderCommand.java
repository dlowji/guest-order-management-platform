package com.dlowji.simple.command.api.commands;

import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
public class CreateOrderCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String accountId;
    private String tableID;
    private OrderStatus orderStatus;
}
