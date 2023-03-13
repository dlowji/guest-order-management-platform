package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.model.DishResponse;
import com.dlowji.simple.command.api.model.OrderDetailResponse;
import com.dlowji.simple.command.api.model.OrderLineItemResponse;
import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.query.api.queries.GetDishByIdQuery;
import com.dlowji.simple.query.api.queries.GetOrderDetailByIdQuery;
import com.dlowji.simple.query.api.queries.GetOrdersQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderProjection {
    private final IOrderRepository orderRepository;
    private final ITableRepository tableRepository;
    private final QueryGateway queryGateway;

    public OrderProjection(IOrderRepository orderRepository, ITableRepository tableRepository, QueryGateway queryGateway) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.queryGateway = queryGateway;
    }

    @QueryHandler
    public List<OrderResponse> handle(GetOrdersQuery getOrdersQuery) {
        List<Order> orders = orderRepository.findAll();

        return orders.stream().map(this::mapToOrderResponse).toList();
    }

    @QueryHandler
    public OrderDetailResponse handle(GetOrderDetailByIdQuery getOrderDetailByIdQuery) {
        String orderId = getOrderDetailByIdQuery.getOrderId();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        return existOrder.map(this::mapToOrderDetailResponse).orElse(null);
    }

    private OrderDetailResponse mapToOrderDetailResponse(Order order) {
        List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
        List<OrderLineItemResponse> orderLineItemResponseList = orderLineItemList.stream().map(this::mapToOrderLineItemResponse).toList();
        String tableId = order.getTableId();
        Optional<SeveredTable> existTable = tableRepository.findById(tableId);
        if (existTable.isPresent()) {
            SeveredTable table = existTable.get();
            OrderDetailResponse response = OrderDetailResponse.builder()
                    .orderId(order.getOrderId())
                    .accountId(order.getAccountId())
                    .tableName(table.getCode())
                    .capacity(table.getCapacity())
                    .orderLineItemResponseList(new ArrayList<>())
                    .orderStatus(order.getOrderStatus())
                    .subTotal(order.getSubTotal())
                    .itemDiscount(order.getItemDiscount())
                    .tax(order.getTax())
                    .total(order.getTotal())
                    .promoCode(order.getPromoCode())
                    .discount(order.getDiscount())
                    .grandTotal(order.getGrandTotal())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();

            orderLineItemResponseList.forEach(item -> response.getOrderLineItemResponseList().add(item));
            return response;
        }

        return null;
    }

    private OrderLineItemResponse mapToOrderLineItemResponse(OrderLineItem orderLineItem) {
        String dishId = orderLineItem.getDishId();
        GetDishByIdQuery getDishByIdQuery = GetDishByIdQuery.builder()
                .dishId(dishId)
                .build();
        DishResponse dishResponse = queryGateway.query(getDishByIdQuery, ResponseTypes.instanceOf(DishResponse.class)).join();
        return OrderLineItemResponse.builder()
                .dishId(orderLineItem.getDishId())
                .title(dishResponse.getTitle())
                .quantity(orderLineItem.getQuantity())
                .price(orderLineItem.getPrice())
                .image(dishResponse.getImage())
                .note(orderLineItem.getNote())
                .build();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        String tableId = order.getTableId();
        Optional<SeveredTable> existTable = tableRepository.findById(tableId);
        if (existTable.isPresent()) {
            SeveredTable table = existTable.get();
            return OrderResponse.builder()
                    .orderId(order.getOrderId())
                    .accountId(order.getAccountId())
                    .tableName(table.getCode())
                    .capacity(table.getCapacity())
                    .orderStatus(order.getOrderStatus())
                    .grandTotal(order.getGrandTotal())
                    .createdAt(order.getCreatedAt())
                    .updatedAt(order.getUpdatedAt())
                    .build();
        }
        return null;
    }
}
