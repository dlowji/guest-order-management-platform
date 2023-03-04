package com.dlowji.simple.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemResponse {
    private String dishId;
    private Integer quantity;
}
