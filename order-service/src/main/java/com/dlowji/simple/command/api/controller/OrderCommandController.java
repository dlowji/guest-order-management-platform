package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.model.CreateOrderRequest;
import com.dlowji.simple.command.api.model.PlaceOrderRequest;
import com.dlowji.simple.command.api.services.OrderCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<String> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return orderCommandService.createOrder(orderRequest);
    }

    @PostMapping("/placed")
    public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) {
        return orderCommandService.placeOrder(placeOrderRequest);
    }
}
