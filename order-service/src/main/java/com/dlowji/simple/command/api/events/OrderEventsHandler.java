package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.IOrderLineItemRepository;
import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.data.OrderLineItem;
import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
                .orderLineItemList(new ArrayList<>())
                .orderStatus(OrderStatus.CREATED)
                .subTotal(BigDecimal.valueOf(0))
                .itemDiscount(BigDecimal.valueOf(0))
                .tax(BigDecimal.valueOf(0))
                .total(BigDecimal.valueOf(0))
                .promoCode("")
                .discount(BigDecimal.valueOf(0))
                .grandTotal(BigDecimal.valueOf(0))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        Optional<Order> result = orderRepository.findById(orderPlacedEvent.getOrderId());
        if (result.isPresent()) {
            Order order = result.get();
            order.setOrderStatus(OrderStatus.PLACED);

            for (Map.Entry<String, Integer> entry : orderPlacedEvent.getSelectedDish().entrySet()) {
                OrderLineItem orderLineItem = OrderLineItem.builder()
                        .dishId(entry.getKey())
                        .quantity(entry.getValue())
                        .unit("món")
                        .price(BigDecimal.valueOf(1200))
                        .build();
                orderLineItemRepository.save(orderLineItem);
                order.addLineItem(orderLineItem);
            }
            orderRepository.save(order);
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
