package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.*;

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
