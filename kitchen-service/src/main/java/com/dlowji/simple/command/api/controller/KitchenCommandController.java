package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.model.CategoryRequest;
import com.dlowji.simple.command.api.model.DishRequest;
import com.dlowji.simple.command.api.service.KitchenCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kitchens")
public class KitchenCommandController {
    private final KitchenCommandService kitchenCommandService;
    @Autowired
    private ICategoryRepository categoryRepository;

    public KitchenCommandController(KitchenCommandService kitchenCommandService) {
        this.kitchenCommandService = kitchenCommandService;
    }

    @PostMapping("/dishes/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> createDish(@RequestBody DishRequest dishRequest) {
        return kitchenCommandService.createDish(dishRequest);
    }

    @PostMapping("/category")
    public String createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .categoryId(categoryRequest.getCategoryId())
                .categoryName(categoryRequest.getCategoryName())
                .build();

        try {
            categoryRepository.save(category);
            return "Success";
        } catch (Exception e) {
            return "False";
        }
    }
}
