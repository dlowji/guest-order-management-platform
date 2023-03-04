package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.model.CreateOrderRequest;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import com.dlowji.simple.command.api.model.PlaceOrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderCommandService {
    private final CommandGateway commandGateway;
    private final IOrderRepository orderRepository;

    public OrderCommandService(CommandGateway commandGateway, IOrderRepository orderRepository) {
        this.commandGateway = commandGateway;
        this.orderRepository = orderRepository;
    }

    public ResponseEntity<?> createOrder(CreateOrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        String userId = orderRequest.getUserId();
        String tableId = orderRequest.getTableId();

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .userId(userId)
                .tableID(tableId)
                .build();

        try {
            commandGateway.send(createOrderCommand);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Order created successfully");
            response.put("orderId", orderId);
            return ResponseEntity.ok(response);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order: " + exception.getMessage());
        }
    }

    public ResponseEntity<String> placeOrder(PlaceOrderRequest placeOrderRequest) {
        String orderId = placeOrderRequest.getOrderId();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        if (existOrder.isEmpty()) {
            return ResponseEntity.badRequest().body("Order is not exist");
        }

        List<OrderLineItemRequest> orderLineItemRequestList = placeOrderRequest.getOrderLineItemRequestList();

        PlaceOrderCommand placeOrderCommand = PlaceOrderCommand.builder()
                .orderId(orderId)
                .orderLineItemRequestList(orderLineItemRequestList)
                .build();

        try {
            commandGateway.send(placeOrderCommand);
            return ResponseEntity.ok("Order placed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order: "+e.getMessage());
        }
    }
}
