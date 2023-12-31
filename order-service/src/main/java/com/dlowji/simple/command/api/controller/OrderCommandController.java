package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.model.CheckoutOrderRequest;
import com.dlowji.simple.command.api.model.CreateOrderRequest;
import com.dlowji.simple.command.api.model.ProgressOrderRequest;
import com.dlowji.simple.command.api.model.UpdatePlacedOrderRequest;
import com.dlowji.simple.command.api.services.OrderCommandService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> placeOrder(@Valid @RequestBody UpdatePlacedOrderRequest updatePlacedOrderRequest) {
        return orderCommandService.updatePlacedOrder(updatePlacedOrderRequest);
    }

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@Valid @RequestBody CheckoutOrderRequest checkoutOrderRequest) {
        return orderCommandService.checkout(checkoutOrderRequest.getOrderId());
    }

    @PostMapping("/progress")
    public ResponseEntity<?> progressOrder(@Valid @RequestBody ProgressOrderRequest progressOrderRequest) {
        System.out.println(progressOrderRequest);

        return orderCommandService.progressOrder(progressOrderRequest);
    }
}
