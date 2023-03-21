package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateOrderCommand;
import com.dlowji.simple.command.api.commands.PlaceOrderCommand;
import com.dlowji.simple.command.api.commands.ProgressOrderCommand;
import com.dlowji.simple.command.api.commands.UpdatePlacedOrderCommand;
import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.enums.OrderLineItemStatus;
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
    private final IOrderLineItemRepository orderLineItemRepository;
    private final ITableRepository tableRepository;
    private final QueryGateway queryGateway;

    public OrderCommandService(CommandGateway commandGateway, IOrderRepository orderRepository, IOrderLineItemRepository orderLineItemRepository, ITableRepository tableRepository, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
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

    public ResponseEntity<?> updatePlacedOrder(UpdatePlacedOrderRequest updatePlacedOrderRequest) {
        String orderId = updatePlacedOrderRequest.getOrderId();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        Map<String, Object> response = new LinkedHashMap<>();
        if (existOrder.isEmpty()) {
            response.put("code", 501);
            response.put("message", "Order is not exist");
            return ResponseEntity.badRequest().body(response);
        }

        List<UpdateOrderLineItemRequest> updateOrderLineItemRequestList = updatePlacedOrderRequest.getUpdateOrderLineItemRequests();
        boolean change = false;
        for (UpdateOrderLineItemRequest updateOrderLineItemRequest : updateOrderLineItemRequestList) {
            Long orderLineItemId = -1L;
            //update
            if (updateOrderLineItemRequest.getOrderLineItemId() != null) {
                orderLineItemId = updateOrderLineItemRequest.getOrderLineItemId();
                if (!orderLineItemRepository.existsById(orderLineItemId)) {
                    response.put("code", 500);
                    response.put("message", "Order line item does not exist");
                    return ResponseEntity.badRequest().body(response);
                } else {
                    OrderLineItem orderLineItem = orderLineItemRepository.findById(orderLineItemId).get();
                    updateOrderLineItemRequest.setPrice(orderLineItem.getPrice());
                    int newQuantity = updateOrderLineItemRequest.getQuantity();
                    String newNote = updateOrderLineItemRequest.getNote();

                    int oldQuantity = orderLineItem.getQuantity();
                    String oldNote = orderLineItem.getNote();
                    if (newQuantity < oldQuantity) {
                        response.put("code", 500);
                        response.put("message", "Invalid quantity");
                        return ResponseEntity.badRequest().body(response);
                    }
                    if (newQuantity > oldQuantity) {
                        updateOrderLineItemRequest.setUpdateQuantity(true);
                        change = true;
                    }
                    if (!Objects.equals(newNote, oldNote)) {
                        updateOrderLineItemRequest.setUpdateNote(true);
                        change = true;
                    }
                    if (!updateOrderLineItemRequest.isUpdateQuantity() && updateOrderLineItemRequest.isUpdateNote() && orderLineItem.getOrderLineItemStatus() != OrderLineItemStatus.UN_COOK) {
                        response.put("code", 500);
                        response.put("message", "Cannot update note of order line item which is preparing or prepared");
                        return ResponseEntity.badRequest().body(response);
                    }
                }
            } else {
                String dishId = updateOrderLineItemRequest.getDishId();
                GetDishByIdQuery getDishByIdQuery = GetDishByIdQuery.builder()
                        .dishId(dishId)
                        .build();
                DishResponse dishResponse = queryGateway.query(getDishByIdQuery, ResponseTypes.instanceOf(DishResponse.class)).join();
                if (null == dishResponse) {
                    response.put("code", 500);
                    response.put("message", "Dish does not exist");
                    return ResponseEntity.badRequest().body(response);
                } else {
                    BigDecimal price = dishResponse.getPrice();
                    updateOrderLineItemRequest.setPrice(price);
                    updateOrderLineItemRequest.setCreate(true);
                    change = true;
                }
            }
        }

        if (!change) {
            response.put("code", 500);
            response.put("message", "Nothing to change or create");
            return ResponseEntity.badRequest().body(response);
        }

        UpdatePlacedOrderCommand updatePlacedOrderCommand = UpdatePlacedOrderCommand.builder()
                .orderId(orderId)
                .updateOrderLineItemRequestList(updateOrderLineItemRequestList)
                .build();
        try {
            commandGateway.sendAndWait(updatePlacedOrderCommand);
            response.put("code", 0);
            response.put("message", "Update placed order successfully");
            response.put("orderId", orderId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error update placed order " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> progressOrder(ProgressOrderRequest progressOrderRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
        ProgressOrderCommand progressOrderCommand = ProgressOrderCommand.builder()
                .orderId(progressOrderRequest.getOrderId())
                .progressOrderLineItemRequestList(progressOrderRequest.getProgressOrderLineItemRequestList())
                .build();
        try {
            commandGateway.send(progressOrderCommand);
            response.put("code", 0);
            response.put("message", "Send signal to begin process order successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error begin processing order " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }

    }
}
