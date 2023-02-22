package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.commands.*;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import com.dlowji.simple.command.api.model.OrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    private final CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create/{userId}")
    public String createOrder(@PathVariable String userId) {
        String orderId = UUID.randomUUID().toString();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .userId(userId)
                .build();

        return commandGateway.sendAndWait(createOrderCommand);
    }

    @PostMapping("/select/{orderId}")
    public String selectDish(@PathVariable String orderId, @RequestBody OrderLineItemRequest orderLineItemRequest) {
        RequestOrderChangeCommand requestOrderChangeCommand = RequestOrderChangeCommand.builder()
                .orderId(orderId)
                .dishId(orderLineItemRequest.getDishId())
                .unit(orderLineItemRequest.getUnit())
                .price(orderLineItemRequest.getPrice())
                .quantity(orderLineItemRequest.getQuantity())
                .select(true)
                .build();

        return commandGateway.sendAndWait(requestOrderChangeCommand);
    }

    @PostMapping("/de-select/{orderId}")
    public String deSelectDish(@PathVariable String orderId, @RequestBody OrderLineItemRequest orderLineItemRequest) {
        RequestOrderChangeCommand requestOrderChangeCommand = RequestOrderChangeCommand.builder()
                .orderId(orderId)
                .dishId(orderLineItemRequest.getDishId())
                .unit(orderLineItemRequest.getUnit())
                .price(orderLineItemRequest.getPrice())
                .quantity(orderLineItemRequest.getQuantity())
                .select(false)
                .build();

        return commandGateway.sendAndWait(requestOrderChangeCommand);
    }

    @PostMapping("/placed/{orderId}")
    public String placeOrder(@PathVariable String orderId) {
        PlaceOrderCommand placeOrderCommand = PlaceOrderCommand.builder()
                .orderId(orderId)
                .build();

        return commandGateway.sendAndWait(placeOrderCommand);
    }
}
