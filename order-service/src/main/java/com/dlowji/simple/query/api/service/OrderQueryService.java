package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderDetailResponse;
import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.model.ScheduleDetailResponse;
import com.dlowji.simple.queries.GetScheduleDetailByIdQuery;
import com.dlowji.simple.query.api.queries.*;
import com.dlowji.simple.utils.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.chrono.ChronoZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderQueryService {
    private final QueryGateway queryGateway;

    public OrderQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public ResponseEntity<?> getOrdersByProperties(Map<String, String> queryParams) {
        Map<String, Object> response = new LinkedHashMap<>();

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
        Map<String, Object> response = new LinkedHashMap<>();
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

    public ResponseEntity<?> getOrderHistoryBySchedule(String authorizationHeader) {
        Map<String, Object> response = new LinkedHashMap<>();
        GetScheduleDetailByIdQuery getScheduleDetailByIdQuery = GetScheduleDetailByIdQuery.builder()
                .scheduleId(authorizationHeader)
                .build();

        ScheduleDetailResponse scheduleDetailResponse = queryGateway.query(getScheduleDetailByIdQuery,
                ResponseTypes.instanceOf(ScheduleDetailResponse.class)).join();
        if (scheduleDetailResponse == null) {
            response.put("code", 700);
            response.put("message", "Schedule does not exist");
            return ResponseEntity.badRequest().body(response);
        }

        GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
        List<OrderResponse> orderResponseList = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        LocalTime startHour = scheduleDetailResponse.getStartWorkHour();
        LocalTime endHour = scheduleDetailResponse.getEndWorkHour();
        LocalDate workDate = scheduleDetailResponse.getWorkDate();

        List<OrderResponse> result = new ArrayList<>();

        for (OrderResponse orderResponse : orderResponseList) {
            ZonedDateTime processTime = orderResponse.getCreatedAt();
            if (processTime.getDayOfMonth() == workDate.getDayOfMonth()
                    && processTime.isAfter(ChronoZonedDateTime.from(startHour))
                    && processTime.isBefore(ChronoZonedDateTime.from(endHour))) {
                result.add(orderResponse);
            }
        }

        response.put("code", 0);
        response.put("message", "get orders by schedule successfully");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getOrderHistoryByDMY(int year, int month, int day, String filter) {
        Map<String, Object> response = new LinkedHashMap<>();

        GetOrdersByYearQuery getOrdersByYearQuery = GetOrdersByYearQuery.builder()
                .year(year)
                .build();
        List<OrderResponse> orderResponseList = queryGateway.query(getOrdersByYearQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();

        if (filter.equals("year")) {
            response.put("code", 0);
            response.put("message", "Filter orders by year successfully");
            response.put("data", orderResponseList);
            return ResponseEntity.ok(response);
        }

        List<OrderResponse> result = new ArrayList<>();

        for (OrderResponse orderResponse : orderResponseList) {
            ZonedDateTime processTime = orderResponse.getCreatedAt();
            if (filter.equals("day") && processTime.getDayOfMonth() == day && processTime.getMonthValue() == month) {
                result.add(orderResponse);
            } else if (filter.equals("month") && processTime.getMonthValue() == month) {
                result.add(orderResponse);
            }
        }

        response.put("code", 0);
        response.put("message", "Filter order by " + filter + " successfully");
        response.put("data", result);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getItemListByOrderId(String orderId) {
        GetOrderDetailByIdQuery getOrderDetailByIdQuery = GetOrderDetailByIdQuery.builder()
                .orderId(orderId)
                .build();
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            OrderDetailResponse orderDetailResponse = queryGateway.query(getOrderDetailByIdQuery, ResponseTypes.instanceOf(OrderDetailResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get item list by order id successfully");
            response.put("data", orderDetailResponse.getOrderLineItemResponseList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "Error getting item list by order id: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
