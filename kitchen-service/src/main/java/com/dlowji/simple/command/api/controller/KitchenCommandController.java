package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.data.Category;
import com.dlowji.simple.command.api.data.ICategoryRepository;
import com.dlowji.simple.command.api.model.CategoryRequest;
import com.dlowji.simple.command.api.model.DishRequest;
import com.dlowji.simple.command.api.model.MarkDoneOrderRequest;
import com.dlowji.simple.command.api.service.KitchenCommandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public CompletableFuture<String> createDish(@Valid @RequestBody DishRequest dishRequest) {
        return kitchenCommandService.createDish(dishRequest);
    }

    @PostMapping("/mark-done")
    public ResponseEntity<?> markDone(@Valid @RequestBody MarkDoneOrderRequest progressOrderRequest) {
        return kitchenCommandService.markDone(progressOrderRequest);
    }

    @PostMapping("/toggle/{dishId}")
    public ResponseEntity<?> toggleDish(@PathVariable String dishId) {
        return kitchenCommandService.toggleDish(dishId);
    }

    @PostMapping("/category")
    public String createCategory(@Valid @RequestBody CategoryRequest categoryRequest) {

        Category category = Category.builder()
                .categoryId(categoryRequest.getCategoryId())
                .categoryName(categoryRequest.getCategoryName())
                .icon(categoryRequest.getIcon())
                .link(categoryRequest.getLink())
                .build();

        try {
            categoryRepository.save(category);
            return "Success";
        } catch (Exception e) {
            return "False";
        }
    }
}
