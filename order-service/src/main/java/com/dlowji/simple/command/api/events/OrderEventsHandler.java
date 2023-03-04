package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Component
@ProcessingGroup("order")
public class OrderEventsHandler {

    private final IOrderRepository orderRepository;

    private final IOrderLineItemRepository orderLineItemRepository;
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        Order order = Order.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .orderStatus(OrderStatus.CREATED)
                .subTotal(BigDecimal.valueOf(0))
                .itemDiscount(BigDecimal.valueOf(0))
                .tax(BigDecimal.valueOf(0))
                .total(BigDecimal.valueOf(0))
                .promoCode("")
                .discount(BigDecimal.valueOf(0))
                .grandTotal(BigDecimal.valueOf(0))
                .build();
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        Optional<Order> result = orderRepository.findById(orderPlacedEvent.getOrderId());
        if (result.isPresent()) {
            Order order = result.get();
            order.setOrderStatus(OrderStatus.IN_PROCESSING);
            List<OrderLineItemRequest> orderLineItemRequestList = orderPlacedEvent.getOrderLineItemRequestList();

            for (OrderLineItemRequest orderLineItemRequest : orderLineItemRequestList) {
                OrderLineItem orderLineItem = OrderLineItem.builder()
                        .dishId(orderLineItemRequest.getDishId())
                        .quantity(orderLineItemRequest.getQuantity())
                        .orderId(order.getOrderId())
                        .build();
                orderLineItemRepository.save(orderLineItem);
//                order.getOrderLineItemList().add(orderLineItem);
            }
            orderRepository.save(order);
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
