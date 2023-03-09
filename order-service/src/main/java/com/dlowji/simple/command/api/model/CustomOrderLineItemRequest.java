package com.dlowji.simple.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomOrderLineItemRequest {
    private String dishId;
    private Integer quantity;
    private BigDecimal price;
}
