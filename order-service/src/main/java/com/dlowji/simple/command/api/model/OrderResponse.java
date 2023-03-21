package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private String orderId;
    private String accountName;
    private String tableName;
    private Integer capacity;
    private List<OrderLineItemResponse> orderLineItemResponseList;
    private OrderStatus orderStatus;
    private LocalDateTime lastProcessing;
    private BigDecimal grandTotal;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
