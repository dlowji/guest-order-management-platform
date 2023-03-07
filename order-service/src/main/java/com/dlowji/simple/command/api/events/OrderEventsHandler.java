package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.IOrderLineItemRepository;
import com.dlowji.simple.command.api.data.IOrderRepository;
import com.dlowji.simple.command.api.data.Order;
import com.dlowji.simple.command.api.data.OrderLineItem;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.model.OrderLineItemRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@ProcessingGroup("order")
public class OrderEventsHandler {

    private final IOrderRepository orderRepository;

    private final IOrderLineItemRepository orderLineItemRepository;
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        Order order = Order.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .tableId(orderCreatedEvent.getTableId())
                .orderStatus(orderCreatedEvent.getOrderStatus())
                .subTotal(BigDecimal.valueOf(0))
                .itemDiscount(BigDecimal.valueOf(0))
                .tax(BigDecimal.valueOf(0))
                .total(BigDecimal.valueOf(0))
                .promoCode("")
                .discount(BigDecimal.valueOf(0))
                .grandTotal(BigDecimal.valueOf(0))
                .build();
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        Optional<Order> existOrder = orderRepository.findById(orderPlacedEvent.getOrderId());
        if (existOrder.isPresent()) {
            Order order = existOrder.get();
            order.setOrderStatus(OrderStatus.IN_PROCESSING);
            List<OrderLineItemRequest> orderLineItemRequestList = orderPlacedEvent.getOrderLineItemRequestList();
            BigDecimal subTotal = order.getSubTotal();
            BigDecimal itemDiscount = order.getItemDiscount();
            BigDecimal discount = order.getDiscount();
            BigDecimal tax = order.getTax();
            for (OrderLineItemRequest orderLineItemRequest : orderLineItemRequestList) {
                OrderLineItem orderLineItem = OrderLineItem.builder()
                        .dishId(orderLineItemRequest.getDishId())
                        .quantity(orderLineItemRequest.getQuantity())
                        .price(orderLineItemRequest.getPrice())
                        .orderId(order.getOrderId())
                        .build();
                orderLineItemRepository.save(orderLineItem);
//                subTotal = subTotal.add(orderLineItem.getPrice());
//                order.getOrderLineItemList().add(orderLineItem);
            }
            BigDecimal total = subTotal.add(tax);
            BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
            order.setSubTotal(subTotal);
            order.setTotal(total);
            order.setGrandTotal(grandTotal);
            orderRepository.save(order);
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
