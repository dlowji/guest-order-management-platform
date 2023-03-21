package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.commands.CreateDishCommand;
import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.command.api.model.DishRequest;
import com.dlowji.simple.command.api.model.MarkDoneOrderRequest;
import com.dlowji.simple.commands.MarkOrderLineItemDoneCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class KitchenCommandService {
    private final CommandGateway commandGateway;

    public KitchenCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
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
        Long orderLineItemId = progressOrderRequest.getMarkDoneOrderLineItemRequests().get(0).getId();
        MarkOrderLineItemDoneCommand markOrderLineItemDoneCommand = MarkOrderLineItemDoneCommand.builder()
                .orderId(orderId)
                .orderLineItemId(orderLineItemId)
                .build();
        try {
            commandGateway.send(markOrderLineItemDoneCommand);
            response.put("code", 0);
            response.put("message", "Mark order line item done successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Mark order line item done failed " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
