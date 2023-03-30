package com.dlowji.simple.command.api.events;

import com.dlowji.simple.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {
    private String orderId;
    private String accountId;
    private String tableId;
    private OrderStatus orderStatus;
}
