package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderResponse;
import com.dlowji.simple.query.api.queries.GetOrdersQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    private OrderResponse mapToOrderResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
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
    }
}
