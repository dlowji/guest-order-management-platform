package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.*;
import com.dlowji.simple.command.api.events.*;
import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
import com.dlowji.simple.commands.MarkOrderLineItemDoneCommandVersion2;
import com.dlowji.simple.commands.MarkOrderLineItemsDoneCommand;
import com.dlowji.simple.enums.OrderStatus;
import com.dlowji.simple.events.OrderLineItemsMarkedDoneEvent;
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
        System.out.println(createOrderCommand);
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
    public void handle(MarkOrderLineItemsDoneCommand markOrderLineItemDoneCommand) {
        System.out.println(markOrderLineItemDoneCommand);
        OrderLineItemsMarkedDoneEvent markedDoneEvent = OrderLineItemsMarkedDoneEvent.builder()
                .orderId(markOrderLineItemDoneCommand.getOrderId())
                .orderLineItemIds(markOrderLineItemDoneCommand.getOrderLineItemIds())
                .build();
        AggregateLifecycle.apply(markedDoneEvent);
    }

    @CommandHandler
    public void handle(MarkOrderLineItemDoneCommandVersion2 markOrderLineItemDoneCommandVersion2) {
        System.out.println(markOrderLineItemDoneCommandVersion2);
    }

    @CommandHandler
    public void handle(ProgressOrderCommand progressOrderCommand) {
        OrderProgressedEvent orderProgressedEvent = OrderProgressedEvent.builder()
                .orderId(progressOrderCommand.getOrderId())
                .progressOrderLineItemRequestList(progressOrderCommand.getProgressOrderLineItemRequestList())
                .build();

        AggregateLifecycle.apply(orderProgressedEvent);
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

    @CommandHandler
    public void handle(UpdatePlacedOrderCommand updatePlacedOrderCommand) {
        PlacedOrderUpdatedEvent placedOrderUpdatedEvent = PlacedOrderUpdatedEvent.builder()
                .orderId(updatePlacedOrderCommand.getOrderId())
                .updateOrderLineItemRequestList(updatePlacedOrderCommand.getUpdateOrderLineItemRequestList())
                .build();

        AggregateLifecycle.apply(placedOrderUpdatedEvent);
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

    @EventSourcingHandler
    public void on(PlacedOrderUpdatedEvent placedOrderUpdatedEvent) {
        this.orderId = placedOrderUpdatedEvent.getOrderId();
    }

    @EventSourcingHandler
    public void on(OrderLineItemsMarkedDoneEvent orderLineItemMarkedDoneEvent) {
        this.orderId = orderLineItemMarkedDoneEvent.getOrderId();
    }

    @EventSourcingHandler
    public void on(OrderProgressedEvent orderProgressedEvent) {
        this.orderId = orderProgressedEvent.getOrderId();
    }
}
