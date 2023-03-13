package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.ITableRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.data.SeveredTable;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.*;
import com.dlowji.simple.query.api.queries.GetAccountByIdQuery;
import com.dlowji.simple.query.api.queries.GetDishByIdQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderCommandService {
    private final CommandGateway commandGateway;
    private final IOrderRepository orderRepository;

    private final ITableRepository tableRepository;
    private final QueryGateway queryGateway;

    public OrderCommandService(CommandGateway commandGateway, IOrderRepository orderRepository, ITableRepository tableRepository, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.queryGateway = queryGateway;
    }

    public ResponseEntity<?> createOrder(CreateOrderRequest orderRequest) {
        String orderId = UUID.randomUUID().toString();
        String accountId = orderRequest.getAccountId();
        String tableId = orderRequest.getTableId();
        Map<String, Object> response = new LinkedHashMap<>();

        GetAccountByIdQuery getAccountByIdQuery = GetAccountByIdQuery.builder()
                .accountId(accountId)
                .build();
        AccountResponse accountResponse = queryGateway.query(getAccountByIdQuery, ResponseTypes.instanceOf(AccountResponse.class)).join();
        System.out.println(accountResponse);
        if (accountResponse == null) {
            response.put("code", 520);
            response.put("message", "Account does not exist");
            return ResponseEntity.badRequest().body(response);
        }
        Optional<SeveredTable> existTable = tableRepository.findById(tableId);
        if (existTable.isEmpty()) {
            response.put("code", 500);
            response.put("message", "Table does not exist");
            return ResponseEntity.badRequest().body(response);
        } else {
            SeveredTable table = existTable.get();
            if (table.getTableStatus() != TableStatus.FREE) {
                response.put("code", 500);
                response.put("message", "Table does not free right now");
                return ResponseEntity.badRequest().body(response);
            }
        }

        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(orderId)
                .accountId(accountId)
                .tableID(tableId)
                .orderStatus(OrderStatus.CREATED)
                .build();
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
        Map<String, Object> response = new LinkedHashMap<>();
        if (existOrder.isEmpty()) {
            response.put("code", 501);
            response.put("message", "Order is not exist");
            return ResponseEntity.badRequest().body(response);
        }

        List<OrderLineItemRequest> orderLineItemRequestList = placeOrderRequest.getOrderLineItemRequestList();
        List<CustomOrderLineItemRequest> customOrderLineItemRequests = new ArrayList<>();

        for (OrderLineItemRequest orderLineItemRequest : orderLineItemRequestList) {
            String dishId = orderLineItemRequest.getDishId();
            GetDishByIdQuery getDishByIdQuery = GetDishByIdQuery.builder()
                    .dishId(dishId)
                    .build();
            DishResponse dishResponse = queryGateway.query(getDishByIdQuery, ResponseTypes.instanceOf(DishResponse.class)).join();
            System.out.println(dishResponse);
            if (null == dishResponse) {
                response.put("code", 500);
                response.put("message", "Dish does not exist");
                return ResponseEntity.badRequest().body(response);
            } else {
                BigDecimal price = dishResponse.getPrice();
                CustomOrderLineItemRequest customOrderLineItemRequest = CustomOrderLineItemRequest.builder()
                        .dishId(dishResponse.getDishId())
                        .quantity(orderLineItemRequest.getQuantity())
                        .price(price)
                        .note(orderLineItemRequest.getNote())
                        .build();
                customOrderLineItemRequests.add(customOrderLineItemRequest);
            }
        }

        PlaceOrderCommand placeOrderCommand = PlaceOrderCommand.builder()
                .orderId(orderId)
                .customOrderLineItemRequests(customOrderLineItemRequests)
                .build();
        try {
            commandGateway.sendAndWait(placeOrderCommand);
            response.put("code", 0);
            response.put("message", "Place order successfully");
            response.put("orderId", orderId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error place order " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
