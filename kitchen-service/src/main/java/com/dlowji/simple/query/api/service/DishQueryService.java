package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.query.api.queries.GetDishByIdQuery;
import com.dlowji.simple.query.api.queries.GetDishesByCategoryQuery;
import com.dlowji.simple.query.api.queries.GetDishesQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class DishQueryService {
    private final QueryGateway queryGateway;

    public DishQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public ResponseEntity<?> getAllDishes() {
        GetDishesQuery getDishesQuery = GetDishesQuery.builder().build();
        Map<String, Object> response = new HashMap<>();

        try {
            List<DishResponse> dishResponseList = queryGateway.query(getDishesQuery, ResponseTypes.multipleInstancesOf(DishResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get all dishes successfully");
            response.put("data", dishResponseList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error all dishes: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> getDishesByCategory(String categoryName) {
        GetDishesByCategoryQuery getDishesByCategoryQuery = GetDishesByCategoryQuery.builder()
                .categoryName(categoryName)
                .build();
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            List<DishResponse> dishResponseList = queryGateway.query(getDishesByCategoryQuery, ResponseTypes.multipleInstancesOf(DishResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get dishes by category successfully");
            response.put("data", dishResponseList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error get dishes by category: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> getDishById(String id) {
        GetDishByIdQuery getDishByIdQuery = GetDishByIdQuery.builder()
                .dishId(id)
                .build();
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            DishResponse dishResponse = queryGateway.query(getDishByIdQuery, ResponseTypes.instanceOf(DishResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get dish by id successfully");
            response.put("data", dishResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error get dish by id: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }

    }
}
