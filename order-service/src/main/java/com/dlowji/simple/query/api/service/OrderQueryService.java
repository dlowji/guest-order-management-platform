package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.data.ITableRepository;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.enums.OrderStatus;
import com.dlowji.simple.model.OrderDetailResponse;
import com.dlowji.simple.model.OrderLineItemResponse;
import com.dlowji.simple.model.OrderResponse;
import com.dlowji.simple.model.ScheduleDetailResponse;
import com.dlowji.simple.queries.GetOrderDetailByIdQuery;
import com.dlowji.simple.queries.GetScheduleDetailByIdQuery;
import com.dlowji.simple.query.api.queries.*;
import com.dlowji.simple.utils.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderQueryService {
    private final QueryGateway queryGateway;
    private final ITableRepository tableRepository;

    public OrderQueryService(QueryGateway queryGateway, ITableRepository tableRepository) {
        this.queryGateway = queryGateway;
        this.tableRepository = tableRepository;
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

        String tableId = queryParams.get("tableId");
        if (!StringUtils.isBlankString(tableId)) {
            GetProcessingOrderByTableIdQuery getProcessingOrderByTableIdQuery = GetProcessingOrderByTableIdQuery
                    .builder()
                    .tableId(tableId)
                    .build();
            try {
                OrderResponse orderResponse = queryGateway.query(getProcessingOrderByTableIdQuery, ResponseTypes.instanceOf(OrderResponse.class)).join();
                response.put("code", 0);
                response.put("message", "Get in processing order by table id successfully");
                response.put("data", orderResponse);
                return ResponseEntity.ok(response);
            } catch (Exception e) {
                response.put("code", 500);
                response.put("message", "Error get in processing order by table id " + e.getMessage());
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

    public ResponseEntity<?> getOrderHistoryBySchedule(String scheduleId) {
        Map<String, Object> response = new LinkedHashMap<>();
        GetScheduleDetailByIdQuery getScheduleDetailByIdQuery = GetScheduleDetailByIdQuery.builder()
                .scheduleId(scheduleId)
                .build();
//
        ScheduleDetailResponse scheduleDetailResponse = queryGateway.query(getScheduleDetailByIdQuery,
                ResponseTypes.instanceOf(ScheduleDetailResponse.class)).join();
        if (scheduleDetailResponse == null) {
            response.put("code", 400);
            response.put("message", "Schedule does not exist");
            return ResponseEntity.badRequest().body(response);
        }
//
        GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
        List<OrderResponse> orderResponseList = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        LocalTime startHour = scheduleDetailResponse.getStartWorkHour();
        LocalTime endHour = scheduleDetailResponse.getEndWorkHour();
        LocalDate workDate = scheduleDetailResponse.getWorkDate();

        List<OrderResponse> result = new ArrayList<>();

        for (OrderResponse orderResponse : orderResponseList) {
            ZonedDateTime processTime = orderResponse.getCreatedAt();
            if (processTime.getDayOfMonth() == workDate.getDayOfMonth()
                    && processTime.toLocalTime().isAfter(startHour)
                    && processTime.toLocalTime().isBefore(endHour)) {
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

    public ResponseEntity<?> getBestSellerDishes(String quantityString) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            int quantity = Integer.parseInt(quantityString);
            GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
            List<OrderResponse> orderResponses = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
            Map<String, Integer> dishQuantities = new HashMap<>();
            for (OrderResponse orderResponse : orderResponses) {
                for (OrderLineItemResponse orderLineItemResponse : orderResponse.getOrderLineItemResponseList()) {
                    dishQuantities.merge(orderLineItemResponse.getTitle(), orderLineItemResponse.getQuantity(), Integer::sum);
                }
            }
            List<Map.Entry<String, Integer>> bestSellers = dishQuantities.entrySet().stream()
                    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                    .limit(quantity)
                    .toList();
            response.put("code", 0);
            response.put("message", "Get top " + quantity + " best seller successfully");
            response.put("data", bestSellers);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("message", "Invalid quantity");
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> getOrdersByDuration(String am, String pm) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h a");
            LocalTime time = LocalTime.parse(pm + " PM", formatter);
            int endHour = time.getHour();
            LocalTime time2 = LocalTime.parse(am + " AM", formatter);
            int startHour = time2.getHour();
            GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
            List<OrderResponse> orderResponses = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
            LocalTime startTime = LocalTime.of(startHour, 0);
            LocalTime endTime = LocalTime.of(endHour, 0);

            Map<Integer, Long> orderCountByHour = orderResponses.stream()
                    .filter(order -> {
                        ZonedDateTime createdAt = order.getCreatedAt();
                        LocalTime localTime = createdAt.toLocalTime();
                        return localTime.isAfter(startTime) && localTime.isBefore(endTime);
                    })
                    .collect(Collectors.groupingBy(
                            order -> order.getCreatedAt().getHour(),
                            Collectors.counting()
                    ));
            response.put("code", 0);
            response.put("message", "Get order count by hour successfully");
            response.put("data", orderCountByHour);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 400);
            response.put("message", "Invalid duration");
            return ResponseEntity.badRequest().body(response);
        }
    }

    public ResponseEntity<?> getHome() {
        Map<String, Object> response = new LinkedHashMap<>();

        LocalDate currentDate = LocalDate.now();
        GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
        List<OrderResponse> orderResponses = queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        List<OrderResponse> currentDateOrders = orderResponses.stream().filter(orderResponse -> orderResponse.getCreatedAt().toLocalDate().equals(currentDate)).toList();
        BigDecimal revenue = currentDateOrders.stream().map(OrderResponse::getGrandTotal).reduce(BigDecimal.valueOf(0), BigDecimal::add);
        int totalOrder = currentDateOrders.size();
        long diningOrders = currentDateOrders.stream().filter(orderResponse -> orderResponse.getOrderStatus() == OrderStatus.IN_PROCESSING).count();
        int freeTables = tableRepository.findAllByTableStatus(TableStatus.FREE).size();
        response.put("code", 0);
        response.put("message", "Get home successfully");
        response.put("revenue", revenue);
        response.put("total orders", totalOrder);
        response.put("dining orders", diningOrders);
        response.put("free tables", freeTables);
        return ResponseEntity.ok(response);
    }
}
