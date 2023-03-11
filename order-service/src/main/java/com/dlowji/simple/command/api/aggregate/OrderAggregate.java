package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.CancelOrderCommand;
import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.events.OrderCanceledEvent;
import com.dlowji.simple.command.api.events.OrderCreatedEvent;
import com.dlowji.simple.command.api.events.OrderPlacedEvent;
import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private String tableId;
    private List<CustomOrderLineItemRequest> selectedDish;
    private OrderStatus orderStatus;
    private boolean confirmed = false;

    public OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        //validation
        OrderCreatedEvent orderCreatedEvent = OrderCreatedEvent.builder()
                .orderId(createOrderCommand.getOrderId())
                .tableId(createOrderCommand.getTableID())
                .accountId(createOrderCommand.getAccountId())
                .orderStatus(createOrderCommand.getOrderStatus())
                .build();
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
                .customOrderLineItemRequests(placeOrderCommand.getCustomOrderLineItemRequests())
                .build();

        AggregateLifecycle.apply(orderPlacedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getAccountId();
        this.tableId = orderCreatedEvent.getTableId();
        this.selectedDish = new ArrayList<>();
        this.orderStatus = orderCreatedEvent.getOrderStatus();
        this.confirmed = false;
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        this.orderId = orderPlacedEvent.getOrderId();
        this.selectedDish = orderPlacedEvent.getCustomOrderLineItemRequests();
        this.orderStatus = OrderStatus.IN_PROCESSING;
        this.confirmed = true;
    }

    @EventSourcingHandler
    public void on(OrderCanceledEvent orderCanceledEvent) {
        this.orderId = orderCanceledEvent.getOrderId();
        this.orderStatus = OrderStatus.CANCELED;
    }
}
