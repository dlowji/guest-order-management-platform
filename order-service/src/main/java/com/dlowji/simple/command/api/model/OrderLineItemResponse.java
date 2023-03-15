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
public class OrderLineItemResponse {
    private Long orderLineItemId;
    private String dishId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
    private String note;
    private String image;
}
