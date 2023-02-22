package com.dlowji.simple.command.api.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishDeSelectedEvent {
    private String orderId;
    private String dishId;
    private Integer quantity;
    private String userId;
}
