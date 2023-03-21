package com.dlowji.simple.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemMarkedDoneEvent {
    private String orderId;
    private Long orderLineItemId;
}
