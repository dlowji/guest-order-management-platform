package com.dlowji.simple.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsMarkedDoneEvent {
    private String orderId;
    private List<Long> orderLineItemIds;
}
