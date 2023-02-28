package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.Dish;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.data.IDishRepository;
import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetDishesByCategoryQuery;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import org.axonframework.queryhandling.QueryHandler;

import java.util.List;

public class KitchenProjection {
    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;

    public KitchenProjection(IDishRepository dishRepository, ICategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
    }

    @QueryHandler
    public List<DishResponse> handle(GetDishesQuery getDishesQuery) {
        List<Dish> dishes = dishRepository.findAll();

        return dishes.stream().map(this::mapToDishResponse).toList();
    }

    @QueryHandler
    public List<DishResponse> handle(GetDishesByCategoryQuery getDishesByCategoryQuery) {
        String categoryName = getDishesByCategoryQuery.getCategoryName();
        Category category = categoryRepository.findByCategoryName(categoryName);
        List<Dish> dishes = dishRepository.findAllByCategory(category);
        return dishes.stream().map(this::mapToDishResponse).toList();
    }

    private DishResponse mapToDishResponse(Dish dish) {
        return DishResponse.builder()
                .dishId(dish.getDishId())
                .title(dish.getTitle())
                .price(dish.getPrice())
                .image(dish.getImage())
                .summary(dish.getSummary())
                .dishStatus(dish.getDishStatus())
                .createdAt(dish.getCreatedAt())
                .updatedAt(dish.getUpdatedAt())
                .build();
    }
}
