package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.CreateDishCommand;
import com.dlowji.simple.command.api.events.DishCreatedEvent;
import com.dlowji.simple.command.api.service.KitchenCommandService;
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
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;

    public KitchenAggregate() {

    }

    @CommandHandler
    public KitchenAggregate(CreateDishCommand createDishCommand) {
        DishCreatedEvent dishCreatedEvent = new DishCreatedEvent();
        BeanUtils.copyProperties(createDishCommand, dishCreatedEvent);
        AggregateLifecycle.apply(dishCreatedEvent);
    }

    @EventSourcingHandler
    public void on(DishCreatedEvent dishCreatedEvent) {
        this.dishId = dishCreatedEvent.getDishId();
        this.title = dishCreatedEvent.getTitle();
        this.image = dishCreatedEvent.getImage();
        this.price = dishCreatedEvent.getPrice();
        this.summary = dishCreatedEvent.getSummary();
        this.dishStatus = dishCreatedEvent.getDishStatus();
    }
}
