package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.CreateOrderRequest;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import com.dlowji.simple.command.api.model.PlaceOrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

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
                .orderStatus(OrderStatus.CREATED)
                .build();
        Map<String, Object> response = new HashMap<>();
        try {
            String orderId2 = commandGateway.sendAndWait(createOrderCommand);
            response.put("code", 0);
            response.put("message", "Create order successfully");
            response.put("orderId", orderId2);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error create order " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> placeOrder(PlaceOrderRequest placeOrderRequest) {
        String orderId = placeOrderRequest.getOrderId();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        if (existOrder.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 501);
            response.put("message", "Order is not exist");
            return ResponseEntity.badRequest().body(response);
        }

        List<OrderLineItemRequest> orderLineItemRequestList = placeOrderRequest.getOrderLineItemRequestList();

        PlaceOrderCommand placeOrderCommand = PlaceOrderCommand.builder()
                .orderId(orderId)
                .orderLineItemRequestList(orderLineItemRequestList)
                .build();
        Map<String, Object> response = new HashMap<>();
        try {
            CompletableFuture<String> orderId2 = commandGateway.send(placeOrderCommand);
            response.put("code", 0);
            response.put("message", "Place order successfully");
            response.put("orderId", orderId2);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error create order " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
