package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.query.api.service.DishQueryService;
import com.dlowji.simple.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kitchens")
public class KitchenQueryController {
    private final DishQueryService dishQueryService;

    public KitchenQueryController(DishQueryService dishQueryService) {
        this.dishQueryService = dishQueryService;
    }

    @GetMapping("/menu")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllDishes(@RequestParam(name = "q", required = false) String categoryName) {

        if (!StringUtils.isBlankString(categoryName)) {
            return dishQueryService.getDishesByCategory(categoryName);
        }

        return dishQueryService.getAllDishes();
    }

    @GetMapping("/menu/status")
    public ResponseEntity<?> getDishesByStatus(@RequestParam(name = "q") String dishStatus) {
            return dishQueryService.getDishesByStatus(dishStatus.toLowerCase());
    }

    @GetMapping("/category")
    public ResponseEntity<?> getAllCategories() {
        return dishQueryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDishByID(@PathVariable String id) {
        return dishQueryService.getDishById(id);
    }
}
