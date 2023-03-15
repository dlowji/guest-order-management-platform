package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
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
            List<CustomOrderLineItemRequest> customOrderLineItemRequests = placedOrderUpdatedEvent.getCustomOrderLineItemRequestList();
            BigDecimal subTotal = order.getSubTotal();
            BigDecimal itemDiscount = order.getItemDiscount();
            BigDecimal discount = order.getDiscount();
            BigDecimal tax = order.getTax();

            for (CustomOrderLineItemRequest customOrderLineItemRequest : customOrderLineItemRequests) {
                String dishId = customOrderLineItemRequest.getDishId();
                int newQuantity = customOrderLineItemRequest.getQuantity();
                int oldQuantity = 0;
                //new dish
                if (orderLineItemList.stream().anyMatch(orderLineItem -> orderLineItem.getDishId().equals(dishId))) {
                    List<OrderLineItem> orderLineItemList1 = orderLineItemList.stream()
                            .filter(orderLineItem1 -> orderLineItem1.getDishId().equals(dishId))
                            .toList();
                    if (orderLineItemList1.size() != 0) {
                        oldQuantity = orderLineItemList1.stream().mapToInt(OrderLineItem::getQuantity).sum();
                    }
                }
                if (newQuantity - oldQuantity <= 0) {
                    continue;
                }
                OrderLineItem orderLineItem = OrderLineItem.builder()
                        .dishId(customOrderLineItemRequest.getDishId())
                        .quantity(newQuantity - oldQuantity)
                        .price(customOrderLineItemRequest.getPrice())
                        .orderId(order.getOrderId())
                        .note(customOrderLineItemRequest.getNote())
                        .build();
                orderLineItemRepository.save(orderLineItem);
                if (customOrderLineItemRequest.getPrice() != null) {
                    BigDecimal result = customOrderLineItemRequest.getPrice().multiply(BigDecimal.valueOf(newQuantity-oldQuantity));
                    subTotal = subTotal.add(result);
                }
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
