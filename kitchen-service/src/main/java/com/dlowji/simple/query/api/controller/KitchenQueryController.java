package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class KitchenQueryController {
    private final QueryGateway queryGateway;

    public KitchenQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<DishResponse> getAllDishes() {
        GetDishesQuery getDishesQuery = new GetDishesQuery();

        return queryGateway.query(getDishesQuery, ResponseTypes.multipleInstancesOf(DishResponse.class)).join();
    }
}
