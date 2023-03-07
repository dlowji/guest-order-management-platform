package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderDetailResponse;
import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.query.api.queries.GetOrderDetailByIdQuery;
import com.dlowji.simple.query.api.queries.GetOrdersByStatusQuery;
import com.dlowji.simple.query.api.queries.GetOrdersByUserIdQuery;
import com.dlowji.simple.query.api.queries.GetOrdersQuery;
import com.dlowji.simple.utils.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderQueryService {
    private final QueryGateway queryGateway;

    public OrderQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public ResponseEntity<?> getOrdersByProperties(Map<String, String> queryParams) {
        Map<String, Object> response = new HashMap<>();

        String status = queryParams.get("status");
        if (!StringUtils.isBlankString(status)) {
            GetOrdersByStatusQuery getOrdersByStatusQuery = GetOrdersByStatusQuery.builder().orderStatus(OrderStatus.valueOf(status.toUpperCase())).build();
            try {
                List<OrderResponse> orderResponses = queryGateway.query(getOrdersByStatusQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
                response.put("code", 0);
                response.put("message", "Get orders by status successfully");
                response.put("data", orderResponses);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("code", 500);
                response.put("message", "Error get orders by status: " + e.getMessage());
                return ResponseEntity.internalServerError().body(response);
            }
        }

        String userId = queryParams.get("userId");
        if (!StringUtils.isBlankString(userId)) {
            GetOrdersByUserIdQuery getOrdersByUserIdQuery = GetOrdersByUserIdQuery.builder().userId(userId).build();
            try {
                List<OrderResponse> orderResponses = queryGateway.query(getOrdersByUserIdQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
                response.put("code", 0);
                response.put("message", "Get orders by user id successfully");
                response.put("data", orderResponses);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("code", 500);
                response.put("message", "Error get orders by user id: " + e.getMessage());
                return ResponseEntity.internalServerError().body(response);
            }
        }

        GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
        try {
            List<OrderResponse> orderResponses = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get all orders successfully");
            response.put("data", orderResponses);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error get all orders: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> getOrderDetail(String id) {
        GetOrderDetailByIdQuery getOrderDetailByIdQuery = GetOrderDetailByIdQuery.builder()
                .orderId(id)
                .build();
        Map<String, Object> response = new HashMap<>();
        try {
            OrderDetailResponse orderDetailResponse = queryGateway.query(getOrderDetailByIdQuery, ResponseTypes.instanceOf(OrderDetailResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get order detail by id successfully");
            response.put("data", orderDetailResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error getting order detail by id: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
