package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.query.api.service.OrderQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
