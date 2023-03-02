package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.*;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.events.*;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String tableId;
    private List<OrderLineItemRequest> selectedDish;
    private OrderStatus orderStatus;
    private boolean confirmed = false;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //validation
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    @CommandHandler
    public void handle(CancelOrderCommand cancelOrderCommand) {
        OrderCanceledEvent orderCanceledEvent = OrderCanceledEvent.builder()
                .orderId(cancelOrderCommand.getOrderId())
                .build();
        AggregateLifecycle.apply(orderCanceledEvent);
    }



    @CommandHandler
    public void handle(PlaceOrderCommand placeOrderCommand) {
        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .orderId(placeOrderCommand.getOrderId())
                .orderLineItemRequestList(placeOrderCommand.getOrderLineItemRequestList())
                .build();

        AggregateLifecycle.apply(orderPlacedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getUserId();
        this.tableId = orderCreatedEvent.getTableId();
        this.selectedDish = new ArrayList<>();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
        this.confirmed = false;
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        this.orderId = orderPlacedEvent.getOrderId();
        this.selectedDish = orderPlacedEvent.getOrderLineItemRequestList();
        this.orderStatus = OrderStatus.IN_PROCESSING;
        this.confirmed = true;
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent orderCanceledEvent) {
        this.orderId = orderCanceledEvent.getOrderId();
        this.orderStatus = OrderStatus.CANCELED;
    }
}
