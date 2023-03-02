package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.commands.CreateDishCommand;
import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.command.api.model.DishRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

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
}
