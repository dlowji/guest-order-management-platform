package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.Dish;
import com.dlowji.simple.command.api.data.IDishRepository;
import org.axonframework.eventhandling.EventHandler;

public class DishEventsHandler {
    private final IDishRepository dishRepository;

    public DishEventsHandler(IDishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @EventHandler
    public void on(DishCreatedEvent dishCreatedEvent) {
        Dish dish = Dish.builder()
                .dishId(dishCreatedEvent.getDishId())
                .title(dishCreatedEvent.getTitle())
                .image(dishCreatedEvent.getImage())
                .price(dishCreatedEvent.getPrice())
                .summary(dishCreatedEvent.getSummary())
                .dishStatus(dishCreatedEvent.getDishStatus())
                .build();
        dishRepository.save(dish);
    }
}
