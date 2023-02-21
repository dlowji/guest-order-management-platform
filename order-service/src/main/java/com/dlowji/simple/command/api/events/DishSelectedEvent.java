package com.dlowji.simple.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishSelectedEvent {
    private String orderId;
    private String dishId;
    private String unit;
    private BigDecimal price;
    private Integer quantity;
}
