package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import com.dlowji.simple.query.api.service.DishQueryService;
import com.dlowji.simple.utils.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kitchens")
public class KitchenQueryController {
    private final DishQueryService dishQueryService;

    public KitchenQueryController(DishQueryService dishQueryService) {
        this.dishQueryService = dishQueryService;
    }

    @GetMapping("/menu")
    @ResponseStatus(HttpStatus.OK)
    public List<DishResponse> getAllDishes(@RequestParam(name = "ca", required = false) String categoryName) {

        if (!StringUtils.isBlankString(categoryName)) {
            return dishQueryService.getDishesByCategory(categoryName);
        }

        return dishQueryService.getAllDishes();
    }
}
