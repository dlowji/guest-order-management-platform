package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.CreateDishCommand;
import com.dlowji.simple.command.api.commands.ToggleDishCommand;
import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.command.api.events.DishCreatedEvent;
import com.dlowji.simple.command.api.events.DishToggledEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class KitchenAggregate {
    @AggregateIdentifier
    private String dishId;
    private String title;
    private BigDecimal price;
    private String summary;
    private String categoryId;
    private DishStatus dishStatus;

    public KitchenAggregate() {

    }

    @CommandHandler
    public KitchenAggregate(CreateDishCommand createDishCommand) {
        DishCreatedEvent dishCreatedEvent = new DishCreatedEvent();
        BeanUtils.copyProperties(createDishCommand, dishCreatedEvent);
        AggregateLifecycle.apply(dishCreatedEvent);
    }

    @CommandHandler
    public void handle(ToggleDishCommand toggleDishCommand) {
        DishToggledEvent dishToggledEvent = DishToggledEvent.builder()
                .dishId(toggleDishCommand.getDishId())
                .build();

        AggregateLifecycle.apply(dishToggledEvent);
    }

    @EventSourcingHandler
    public void on(DishToggledEvent dishToggledEvent) {
        this.dishId = dishToggledEvent.getDishId();
    }

    @EventSourcingHandler
    public void on(DishCreatedEvent dishCreatedEvent) {
        this.dishId = dishCreatedEvent.getDishId();
        this.title = dishCreatedEvent.getTitle();
        this.price = dishCreatedEvent.getPrice();
        this.summary = dishCreatedEvent.getSummary();
        this.categoryId = dishCreatedEvent.getCategoryId();
        this.dishStatus = dishCreatedEvent.getDishStatus();
    }
}
