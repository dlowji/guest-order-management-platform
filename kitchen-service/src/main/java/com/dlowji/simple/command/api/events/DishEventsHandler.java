package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.Dish;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.data.IDishRepository;
import com.dlowji.simple.command.api.enums.DishStatus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DishEventsHandler {
    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;

    public DishEventsHandler(IDishRepository dishRepository, ICategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
    }

    @EventHandler
    public void on(DishCreatedEvent dishCreatedEvent) {
        String categoryId = dishCreatedEvent.getCategoryId();
        Optional<Category> existCategory = categoryRepository.findById(categoryId);
        if (existCategory.isPresent()) {
            Category category = existCategory.get();
            Dish dish = Dish.builder()
                    .dishId(dishCreatedEvent.getDishId())
                    .title(dishCreatedEvent.getTitle())
                    .image(dishCreatedEvent.getImage())
                    .price(dishCreatedEvent.getPrice())
                    .summary(dishCreatedEvent.getSummary())
                    .category(category)
                    .dishStatus(dishCreatedEvent.getDishStatus())
                    .build();
            dishRepository.save(dish);
        }
    }

    @EventHandler
    public void on(DishToggledEvent dishToggledEvent) {
        String dishId = dishToggledEvent.getDishId();
        Optional<Dish> existDish = dishRepository.findById(dishId);
        if (existDish.isPresent()) {
            Dish dish = existDish.get();
            if (dish.getDishStatus() == DishStatus.AVAILABLE) {
                dish.setDishStatus(DishStatus.UN_AVAILABLE);
            } else {
                dish.setDishStatus(DishStatus.AVAILABLE);
            }
        }
    }
}
