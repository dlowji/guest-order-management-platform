package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
import com.dlowji.simple.command.api.model.UpdateOrderLineItemRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@ProcessingGroup("order")
public class OrderEventsHandler {

    private final IOrderRepository orderRepository;

    private final IOrderLineItemRepository orderLineItemRepository;
    private final ITableRepository tableRepository;
    @EventHandler
    public void on(OrderCreatedEvent orderCreatedEvent) {
        String tableId = orderCreatedEvent.getTableId();
        Optional<SeveredTable> existTable = tableRepository.findById(tableId);
        if (existTable.isPresent()) {
            SeveredTable table = existTable.get();
            table.setTableStatus(TableStatus.OCCUPIED);
            Order order = Order.builder()
                    .orderId(orderCreatedEvent.getOrderId())
                    .accountId(orderCreatedEvent.getAccountId())
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
            tableRepository.save(table);
            orderRepository.save(order);
        }
    }

    @EventHandler
    public void on(OrderPlacedEvent orderPlacedEvent) {
        Optional<Order> existOrder = orderRepository.findById(orderPlacedEvent.getOrderId());
        if (existOrder.isPresent()) {
            Order order = existOrder.get();
            String tableId = order.getTableId();
            Optional<SeveredTable> existTable = tableRepository.findById(tableId);
            if (existTable.isPresent()) {
                SeveredTable table = existTable.get();
                order.setOrderStatus(OrderStatus.IN_PROCESSING);
                List<CustomOrderLineItemRequest> customOrderLineItemRequests = orderPlacedEvent.getCustomOrderLineItemRequests();
                BigDecimal subTotal = order.getSubTotal();
                BigDecimal itemDiscount = order.getItemDiscount();
                BigDecimal discount = order.getDiscount();
                BigDecimal tax = order.getTax();
                for (CustomOrderLineItemRequest customOrderLineItemRequest : customOrderLineItemRequests) {
                    OrderLineItem orderLineItem = OrderLineItem.builder()
                            .dishId(customOrderLineItemRequest.getDishId())
                            .quantity(customOrderLineItemRequest.getQuantity())
                            .price(customOrderLineItemRequest.getPrice())
                            .orderId(order.getOrderId())
                            .note(customOrderLineItemRequest.getNote())
                            .build();
                    orderLineItemRepository.save(orderLineItem);
                    if (customOrderLineItemRequest.getPrice() != null) {
                        BigDecimal result = customOrderLineItemRequest.getPrice().multiply(BigDecimal.valueOf(customOrderLineItemRequest.getQuantity()));
                        subTotal = subTotal.add(result);
                    }
                }
                BigDecimal total = subTotal.add(tax);
                BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
                order.setSubTotal(subTotal);
                order.setTotal(total);
                order.setGrandTotal(grandTotal);
                tableRepository.save(table);
                orderRepository.save(order);
            }
        }
    }

    @EventHandler
    public void on(PlacedOrderUpdatedEvent placedOrderUpdatedEvent) {
        Optional<Order> existOrder = orderRepository.findById(placedOrderUpdatedEvent.getOrderId());
        if (existOrder.isPresent()) {
            Order order = existOrder.get();
            List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
            List<UpdateOrderLineItemRequest> updateOrderLineItemRequestList = placedOrderUpdatedEvent.getUpdateOrderLineItemRequestList();
            BigDecimal subTotal = order.getSubTotal();
            BigDecimal itemDiscount = order.getItemDiscount();
            BigDecimal discount = order.getDiscount();
            BigDecimal tax = order.getTax();

            for (UpdateOrderLineItemRequest updateOrderLineItemRequest : updateOrderLineItemRequestList) {
                Long orderLineItemId = updateOrderLineItemRequest.getOrderLineItemId();
                OrderLineItem existOrderLineItem = orderLineItemList.stream().filter(orderLineItem -> Objects.equals(orderLineItem.getId(), orderLineItemId))
                        .findFirst()
                        .orElse(null);
                if (existOrderLineItem != null) {
                    if (updateOrderLineItemRequest.isUpdateNote()) {
                        String updateNote = updateOrderLineItemRequest.getNote();
                        existOrderLineItem.setNote(updateNote);
                        orderLineItemRepository.save(existOrderLineItem);
                    } else if (updateOrderLineItemRequest.isUpdateQuantity()) {
                        int newQuantity = updateOrderLineItemRequest.getQuantity();
                        int oldQuantity = existOrderLineItem.getQuantity();
                        int updateQuantity = Math.abs(newQuantity - oldQuantity);
                        existOrderLineItem.setQuantity(updateQuantity + oldQuantity);
                        BigDecimal result = existOrderLineItem.getPrice().multiply(BigDecimal.valueOf(updateQuantity));
                        subTotal = subTotal.add(result);
                        BigDecimal total = subTotal.add(tax);
                        BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
                        order.setSubTotal(subTotal);
                        order.setTotal(total);
                        order.setGrandTotal(grandTotal);
                        try {
                            orderLineItemRepository.save(existOrderLineItem);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        orderRepository.save(order);
                    }
                }
            }
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
