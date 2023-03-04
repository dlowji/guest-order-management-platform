package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.data.OrderLineItem;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderDetailResponse;
import com.dlowji.simple.command.api.model.OrderLineItemResponse;
import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.query.api.queries.GetOrderDetailByIdQuery;
import com.dlowji.simple.query.api.queries.GetOrdersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class OrderProjection {
    private final IOrderRepository orderRepository;

    public OrderProjection(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
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
        orderLineItemList.forEach(System.out::println);
        List<OrderLineItemResponse> orderLineItemResponseList = orderLineItemList.stream().map(this::mapToOrderLineItemResponse).toList();
        orderLineItemResponseList.forEach(System.out::println);
        OrderDetailResponse response = OrderDetailResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
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

    private OrderLineItemResponse mapToOrderLineItemResponse(OrderLineItem orderLineItem) {
        return OrderLineItemResponse.builder()
                .dishId(orderLineItem.getDishId())
                .quantity(orderLineItem.getQuantity())
                .build();
    }

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .grandTotal(order.getGrandTotal())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
}
