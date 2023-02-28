package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetDishesByCategoryQuery;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishQueryService {
    private final QueryGateway queryGateway;

    public DishQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public List<DishResponse> getAllDishes() {
        GetDishesQuery getDishesQuery = GetDishesQuery.builder().build();
        return queryGateway.query(getDishesQuery, ResponseTypes.multipleInstancesOf(DishResponse.class)).join();
    }

    public List<DishResponse> getDishesByCategory(String categoryName) {
        GetDishesByCategoryQuery getDishesByCategoryQuery = GetDishesByCategoryQuery.builder()
                .categoryName(categoryName)
                .build();
        return queryGateway.query(getDishesByCategoryQuery, ResponseTypes.multipleInstancesOf(DishResponse.class)).join();
    }
}
