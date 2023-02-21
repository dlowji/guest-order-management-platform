package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestOrderChangeCommand {
    private String orderId;
    private String dishId;
    private String unit;
    private BigDecimal price;
    private Integer quantity;

    private boolean select;
}
