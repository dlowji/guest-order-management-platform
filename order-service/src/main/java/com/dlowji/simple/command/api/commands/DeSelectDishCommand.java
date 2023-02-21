package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeSelectDishCommand {
    @TargetAggregateIdentifier
    private String orderId;
    private String dishId;
    private String unit;
    private BigDecimal price;
    private Integer quantity;
}
