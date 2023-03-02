package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.model.DishRequest;
import com.dlowji.simple.command.api.service.KitchenCommandService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/kitchens")
public class KitchenCommandController {
    private final KitchenCommandService kitchenCommandService;

    public KitchenCommandController(KitchenCommandService kitchenCommandService) {
        this.kitchenCommandService = kitchenCommandService;
    }

    @PostMapping("/dishes/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> createDish(@RequestBody DishRequest dishRequest) {
        return kitchenCommandService.createDish(dishRequest);
    }
}
