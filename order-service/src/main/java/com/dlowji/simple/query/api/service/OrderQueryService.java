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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderQueryService {
    private final QueryGateway queryGateway;

    public OrderQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public List<OrderResponse> getOrdersByProperties(Map<String, String> queryParams) {
        String status = queryParams.get("status");
        if (!StringUtils.isBlankString(status)) {
            GetOrdersByStatusQuery getOrdersByStatusQuery = GetOrdersByStatusQuery.builder().orderStatus(OrderStatus.valueOf(status)).build();
            return queryGateway.query(getOrdersByStatusQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        }

        String userId = queryParams.get("userId");
        if (!StringUtils.isBlankString(userId)) {
            GetOrdersByUserIdQuery getOrdersByUserIdQuery = GetOrdersByUserIdQuery.builder().userId(userId).build();
            return queryGateway.query(getOrdersByUserIdQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
        }

        GetOrdersQuery getOrdersQuery = GetOrdersQuery.builder().build();
        return queryGateway.query(getOrdersQuery, ResponseTypes.multipleInstancesOf(OrderResponse.class)).join();
    }

    public OrderDetailResponse getOrderDetail(String id) {
        GetOrderDetailByIdQuery getOrderDetailByIdQuery = GetOrderDetailByIdQuery.builder()
                .orderId(id)
                .build();

        return queryGateway.query(getOrderDetailByIdQuery, ResponseTypes.instanceOf(OrderDetailResponse.class)).join();
    }
}
