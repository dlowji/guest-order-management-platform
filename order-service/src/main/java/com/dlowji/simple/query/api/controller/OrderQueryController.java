package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.query.api.service.OrderQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderQueryController {
    private final OrderQueryService orderQueryService;

    public OrderQueryController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping()
    public ResponseEntity<?> getAllOrders(@RequestParam Map<String, String> queryParams) {
        return orderQueryService.getOrdersByProperties(queryParams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        return orderQueryService.getOrderDetail(id);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItemListByOrderId(@PathVariable String id) {
        return orderQueryService.getItemListByOrderId(id);
    }

    @GetMapping("/history/{scheduleId}")
    public ResponseEntity<?> getOrderHistoryBySchedule(@PathVariable String scheduleId) {
        return orderQueryService.getOrderHistoryBySchedule(scheduleId);
    }

    @GetMapping("/best-seller/{quantity}")
    public ResponseEntity<?> getBestSellerDishes(@PathVariable String quantity) {
        return orderQueryService.getBestSellerDishes(quantity);
    }

    @GetMapping("/{filter}")
    public ResponseEntity<?> getOrdersByDMY(@PathVariable String filter) {
        LocalDate current = LocalDate.now();
        return orderQueryService.getOrderHistoryByDMY(current.getYear(), current.getMonthValue(), current.getDayOfMonth(), filter);
    }

    @GetMapping("/duration/{am}/{pm}")
    public ResponseEntity<?> getOrdersByDuration(@PathVariable String am, @PathVariable String pm) {
        return orderQueryService.getOrdersByDuration(am, pm);
    }

    @GetMapping("/home")
    public ResponseEntity<?> getHome() {
        return orderQueryService.getHome();
    }
}
