package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.model.CreateOrderRequest;
import com.dlowji.simple.command.api.model.PlaceOrderRequest;
import com.dlowji.simple.command.api.model.UpdatePlacedOrderRequest;
import com.dlowji.simple.command.api.services.OrderCommandService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private final OrderCommandService orderCommandService;

    public OrderCommandController(OrderCommandService orderCommandService) {
        this.orderCommandService = orderCommandService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest orderRequest) {
        return orderCommandService.createOrder(orderRequest);
    }

    @PostMapping("/placed")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody PlaceOrderRequest placeOrderRequest) {
        return orderCommandService.placeOrder(placeOrderRequest);
    }

    @PostMapping("/placed/update")
    public ResponseEntity<?> updatePlacedOrder(@Valid @RequestBody UpdatePlacedOrderRequest updatePlacedOrderRequest) {
        return orderCommandService.updatePlacedOrder(updatePlacedOrderRequest);
    }
}
