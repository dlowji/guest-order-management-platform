package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private String accountId;
    private String tableId;
    private OrderStatus orderStatus;
    private BigDecimal grandTotal;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
