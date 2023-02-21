package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.query.api.queries.GetOrdersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderQueryController {

    private final QueryGateway queryGateway;

    public OrderQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping()
    public List<OrderResponse> getAllOrders() {
        GetOrdersQuery getOrdersQuery = new GetOrdersQuery();

        return queryGateway.query(getOrdersQuery,
                ResponseTypes.multipleInstancesOf(OrderResponse.class))
                .join();
    }
}
