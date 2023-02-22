package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.DeSelectDishCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.commands.SelectDishCommand;
import com.dlowji.simple.command.api.events.DishDeSelectedEvent;
import com.dlowji.simple.command.api.events.DishSelectedEvent;
import com.dlowji.simple.command.api.events.OrderCreatedEvent;
import com.dlowji.simple.command.api.events.OrderPlacedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String userId;
    private Map<String, Integer> selectedDish;
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
    public void handle(SelectDishCommand selectDishCommand) {
        //validation
        DishSelectedEvent dishSelectedEvent = DishSelectedEvent.builder()
                .orderId(selectDishCommand.getOrderId())
                .userId(selectDishCommand.getUserId())
                .dishId(selectDishCommand.getDishId())
                .quantity(selectDishCommand.getQuantity())
                .build();

        AggregateLifecycle.apply(dishSelectedEvent);
    }

    @CommandHandler
    public void handle(DeSelectDishCommand deSelectDishCommand) {
        DishDeSelectedEvent dishDeSelectedEvent = DishDeSelectedEvent.builder()
                .orderId(deSelectDishCommand.getOrderId())
                .userId(deSelectDishCommand.getUserId())
                .dishId(deSelectDishCommand.getDishId())
                .quantity(deSelectDishCommand.getQuantity())
                .build();

        AggregateLifecycle.apply(dishDeSelectedEvent);
    }

    @CommandHandler
    public void handle(PlaceOrderCommand placeOrderCommand) {
        OrderPlacedEvent orderPlacedEvent = OrderPlacedEvent.builder()
                .orderId(placeOrderCommand.getOrderId())
                .selectedDish(selectedDish)
                .build();

        AggregateLifecycle.apply(orderPlacedEvent);
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getUserId();
        this.selectedDish = new HashMap<>();
        this.confirmed = true;
    }

    @EventSourcingHandler
    public void on(DishSelectedEvent dishSelectedEvent) {
        this.orderId = dishSelectedEvent.getOrderId();
        this.userId = dishSelectedEvent.getUserId();
        this.confirmed = false;

        String dishId = dishSelectedEvent.getDishId();
        if (selectedDish.containsKey(dishId)) {
            int oldQuantity = selectedDish.get(dishId);
            selectedDish.replace(dishId, oldQuantity + dishSelectedEvent.getQuantity());
        } else {
            selectedDish.put(dishId, dishSelectedEvent.getQuantity());
        }
    }

    @EventSourcingHandler
    public void on(DishDeSelectedEvent dishDeSelectedEvent) {
        this.orderId = dishDeSelectedEvent.getOrderId();
        this.userId = dishDeSelectedEvent.getUserId();
        this.confirmed = false;

        String dishId = dishDeSelectedEvent.getDishId();
        if (selectedDish.containsKey(dishId)) {
            int oldQuantity = selectedDish.get(dishId);
            selectedDish.replace(dishId, oldQuantity - dishDeSelectedEvent.getQuantity());
        }
    }

    @EventSourcingHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        this.orderId = orderPlacedEvent.getOrderId();
        this.userId = orderPlacedEvent.getUserId();
        this.confirmed = true;
    }
}
