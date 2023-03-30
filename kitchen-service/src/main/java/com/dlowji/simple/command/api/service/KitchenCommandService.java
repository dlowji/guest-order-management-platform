package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.commands.CreateDishCommand;
import com.dlowji.simple.command.api.commands.ToggleDishCommand;
import com.dlowji.simple.command.api.data.IDishRepository;
import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.command.api.model.DishRequest;
import com.dlowji.simple.command.api.model.MarkDoneOrderLineItemRequest;
import com.dlowji.simple.command.api.model.MarkDoneOrderRequest;
import com.dlowji.simple.commands.MarkOrderLineItemsDoneCommand;
import com.dlowji.simple.model.OrderDetailResponse;
import com.dlowji.simple.model.OrderLineItemResponse;
import com.dlowji.simple.queries.GetOrderDetailByIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class KitchenCommandService {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final IDishRepository dishRepository;

    public KitchenCommandService(CommandGateway commandGateway, QueryGateway queryGateway, IDishRepository dishRepository) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.dishRepository = dishRepository;
    }

    public CompletableFuture<String> createDish(DishRequest dishRequest) {
        String dishId = UUID.randomUUID().toString();
        CreateDishCommand createDishCommand = CreateDishCommand.builder()
                .dishId(dishId)
                .title(dishRequest.getTitle())
                .image(dishRequest.getImage())
                .price(dishRequest.getPrice())
                .summary(dishRequest.getSummary())
                .categoryId(dishRequest.getCategoryId())
                .dishStatus(DishStatus.AVAILABLE)
                .build();
        return commandGateway.send(createDishCommand);
    }

    public ResponseEntity<?> markDone(MarkDoneOrderRequest progressOrderRequest) {
        Map<String, Object> response = new LinkedHashMap<>();

        String orderId = progressOrderRequest.getOrderId();
        GetOrderDetailByIdQuery getOrderDetailByIdQuery = GetOrderDetailByIdQuery.builder()
                .orderId(orderId)
                .build();
        OrderDetailResponse orderDetailResponse = queryGateway.query(getOrderDetailByIdQuery, ResponseTypes.instanceOf(OrderDetailResponse.class)).join();

        if (orderDetailResponse == null) {
            response.put("code", 401);
            response.put("message", "Does not exist order with corresponding id");
            return ResponseEntity.badRequest().body(response);
        }

        List<Long> orderLineItemIds = progressOrderRequest.getMarkDoneOrderLineItemRequests().stream().map(MarkDoneOrderLineItemRequest::getId).toList();
        List<OrderLineItemResponse> orderLineItemResponseList = orderDetailResponse.getOrderLineItemResponseList();
        for (Long orderLineItemId : orderLineItemIds) {
            if (orderLineItemResponseList.stream().noneMatch(orderLineItemResponse -> Objects.equals(orderLineItemResponse.getOrderLineItemId(), orderLineItemId))) {
                response.put("code", 401);
                response.put("message", "Wrong order line item in this order");
                return ResponseEntity.badRequest().body(response);
            }
        }
        MarkOrderLineItemsDoneCommand markOrderLineItemsDoneCommand = MarkOrderLineItemsDoneCommand.builder()
                .orderId(orderId)
                .orderLineItemIds(new ArrayList<>())
                .build();
        for (Long orderLineItemId : orderLineItemIds) {
            markOrderLineItemsDoneCommand.getOrderLineItemIds().add(orderLineItemId);
        }
        try {
            commandGateway.send(markOrderLineItemsDoneCommand);
            response.put("code", 0);
            response.put("message", "Mark order line items done successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Mark order line item done failed " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> toggleDish(String dishId) {
        Map<String, Object> response = new LinkedHashMap<>();

        if (dishRepository.findById(dishId).isEmpty()) {
            response.put("code", 401);
            response.put("message", "Dish not exist");
            return ResponseEntity.badRequest().body(response);
        }

        ToggleDishCommand toggleDishCommand = ToggleDishCommand.builder()
                .dishId(dishId)
                .build();

        try {
            commandGateway.send(toggleDishCommand);
            response.put("code", 0);
            response.put("message", "Send signal to toggle dish successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error toggle dish: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
