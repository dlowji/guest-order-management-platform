package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.Dish;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.data.IDishRepository;
import com.dlowji.simple.command.api.model.CategoryResponse;
import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetCategoriesQuery;
import com.dlowji.simple.query.api.queries.GetDishByIdQuery;
import com.dlowji.simple.query.api.queries.GetDishesByCategoryQuery;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Component
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
        String categoryName = StringUtils.capitalize(getDishesByCategoryQuery.getCategoryName().toLowerCase());
        Category category = categoryRepository.findByCategoryName(categoryName);
        List<Dish> dishes = dishRepository.findAllByCategory(category);
        return dishes.stream().map(this::mapToDishResponse).toList();
    }

    @QueryHandler
    public DishResponse handle(GetDishByIdQuery getDishByIdQuery) {
        Optional<Dish> existDish = dishRepository.findById(getDishByIdQuery.getDishId());
        return existDish.map(this::mapToDishResponse).orElse(null);

    }

    @QueryHandler
    public List<CategoryResponse> handle(GetCategoriesQuery getCategoriesQuery) {
        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream().map(this::mapToCategoryResponse).toList();
    }

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .icon(category.getIcon())
                .link(category.getLink())
                .build();
    }

    private DishResponse mapToDishResponse(Dish dish) {
        return DishResponse.builder()
                .dishId(dish.getDishId())
                .title(dish.getTitle())
                .price(dish.getPrice())
                .image(dish.getImage())
                .summary(dish.getSummary())
                .dishStatus(dish.getDishStatus().toString())
                .categoryName(dish.getCategory().getCategoryName())
                .createdAt(dish.getCreatedAt())
                .updatedAt(dish.getUpdatedAt())
                .build();
    }
}
