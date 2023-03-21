package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.enums.OrderLineItemStatus;
import com.dlowji.simple.command.api.enums.OrderStatus;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.CustomOrderLineItemRequest;
import com.dlowji.simple.command.api.model.ProgressOrderLineItemRequest;
import com.dlowji.simple.command.api.model.UpdateOrderLineItemRequest;
import com.dlowji.simple.events.OrderLineItemMarkedDoneEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                if (updateOrderLineItemRequest.isCreate()) {
                    String newNote = updateOrderLineItemRequest.getNote();
                    OrderLineItem orderLineItem = OrderLineItem.builder()
                            .dishId(updateOrderLineItemRequest.getDishId())
                            .quantity(updateOrderLineItemRequest.getQuantity())
                            .price(updateOrderLineItemRequest.getPrice())
                            .orderId(order.getOrderId())
                            .note(newNote)
                            .orderLineItemStatus(OrderLineItemStatus.UN_COOK)
                            .build();
                    BigDecimal result = orderLineItem.getPrice().multiply(BigDecimal.valueOf(updateOrderLineItemRequest.getQuantity()));
                    subTotal = subTotal.add(result);
                    BigDecimal total = subTotal.add(tax);
                    BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
                    order.setSubTotal(subTotal);
                    order.setTotal(total);
                    order.setGrandTotal(grandTotal);
                    orderLineItemRepository.save(orderLineItem);
                    orderRepository.save(order);
                } else {
                    Long orderLineItemId = updateOrderLineItemRequest.getOrderLineItemId();
                    OrderLineItem existOrderLineItem = orderLineItemList.stream().filter(orderLineItem -> Objects.equals(orderLineItem.getId(), orderLineItemId))
                            .findFirst()
                            .orElse(null);
                    if (existOrderLineItem != null) {
                        int newQuantity = updateOrderLineItemRequest.getQuantity();
                        int oldQuantity = existOrderLineItem.getQuantity();
                        int moreQuantity = newQuantity - oldQuantity;
                        String newNote = updateOrderLineItemRequest.getNote();
                        if (updateOrderLineItemRequest.isUpdateNote() && updateOrderLineItemRequest.isUpdateQuantity()) {
                            if (existOrderLineItem.getOrderLineItemStatus() == OrderLineItemStatus.UN_COOK) {
                                String oldNote = existOrderLineItem.getNote();
                                if (!oldNote.equals(newNote)) {
                                    existOrderLineItem.setNote(oldNote + " " + moreQuantity + " " + newNote);
                                }
                                existOrderLineItem.setQuantity(oldQuantity + moreQuantity);
                            } else {
                                OrderLineItem orderLineItem = OrderLineItem.builder()
                                        .dishId(updateOrderLineItemRequest.getDishId())
                                        .quantity(moreQuantity)
                                        .price(existOrderLineItem.getPrice())
                                        .orderId(order.getOrderId())
                                        .note(newNote)
                                        .orderLineItemStatus(OrderLineItemStatus.UN_COOK)
                                        .build();
                                orderLineItemRepository.save(orderLineItem);
                            }
                        } else if (updateOrderLineItemRequest.isUpdateQuantity()) {
                            if (existOrderLineItem.getOrderLineItemStatus() == OrderLineItemStatus.UN_COOK) {
                                existOrderLineItem.setQuantity(oldQuantity + moreQuantity);
                            } else {
                                OrderLineItem orderLineItem = OrderLineItem.builder()
                                        .dishId(updateOrderLineItemRequest.getDishId())
                                        .quantity(moreQuantity)
                                        .price(existOrderLineItem.getPrice())
                                        .orderId(order.getOrderId())
                                        .note(newNote)
                                        .orderLineItemStatus(OrderLineItemStatus.UN_COOK)
                                        .build();
                                orderLineItemRepository.save(orderLineItem);
                            }
                        } else if (updateOrderLineItemRequest.isUpdateNote()) {
                            existOrderLineItem.setNote(newNote);
                        }
                        BigDecimal result = existOrderLineItem.getPrice().multiply(BigDecimal.valueOf(moreQuantity));
                        subTotal = subTotal.add(result);
                        BigDecimal total = subTotal.add(tax);
                        BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
                        order.setSubTotal(subTotal);
                        order.setTotal(total);
                        order.setGrandTotal(grandTotal);
                        orderLineItemRepository.save(existOrderLineItem);
                        orderRepository.save(order);
                    }
                }
            }
        }
    }

    @EventHandler
    public void on(OrderProgressedEvent orderProgressedEvent) {
        String orderId = orderProgressedEvent.getOrderId();
        List<ProgressOrderLineItemRequest> progressOrderLineItemRequestList = orderProgressedEvent.getProgressOrderLineItemRequestList();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        if (existOrder.isPresent()) {
            Order order = existOrder.get();
            List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
            for (ProgressOrderLineItemRequest progressOrderLineItemRequest : progressOrderLineItemRequestList) {
                OrderLineItem orderLineItem = orderLineItemList.stream().filter(item -> Objects.equals(item.getId(), progressOrderLineItemRequest.getId()))
                        .findFirst()
                        .get();
                if (progressOrderLineItemRequest.getOrderLineItemStatus() == OrderLineItemStatus.STOCK_OUT) {
                    orderLineItem.setOrderLineItemStatus(OrderLineItemStatus.STOCK_OUT);
//                    orderLineItemList.remove(orderLineItem);
                    BigDecimal result = orderLineItem.getPrice().multiply(BigDecimal.valueOf(progressOrderLineItemRequest.getQuantity()));
                    BigDecimal subTotal = order.getSubTotal();
                    BigDecimal tax = order.getTax();
                    BigDecimal itemDiscount = order.getItemDiscount();
                    BigDecimal discount = order.getDiscount();
                    subTotal = subTotal.subtract(result);
                    BigDecimal total = subTotal.add(tax);
                    BigDecimal grandTotal = total.subtract(itemDiscount).subtract(discount);
                    order.setSubTotal(subTotal);
                    order.setTotal(total);
                    order.setGrandTotal(grandTotal);
                } else {
                    orderLineItem.setOrderLineItemStatus(OrderLineItemStatus.COOKING);
                }
                orderLineItemRepository.save(orderLineItem);
            }
            LocalDateTime current = LocalDateTime.now();
            order.setOrderStatus(OrderStatus.IN_PROCESSING);
            order.setLastProcessing(current);
            orderRepository.save(order);
        }
    }

    @EventHandler
    public void on(OrderLineItemMarkedDoneEvent orderLineItemMarkedDoneEvent) {
        String orderId = orderLineItemMarkedDoneEvent.getOrderId();
        Long orderLineItemId = orderLineItemMarkedDoneEvent.getOrderLineItemId();
        Optional<Order> existOrder = orderRepository.findById(orderId);
        if (existOrder.isPresent()) {
            Order order = existOrder.get();
            List<OrderLineItem> orderLineItemList = order.getOrderLineItemList();
            OrderLineItem orderLineItem = orderLineItemList.stream().filter(item -> Objects.equals(item.getId(), orderLineItemId)).findFirst().get();
            orderLineItem.setOrderLineItemStatus(OrderLineItemStatus.COOKED);

            LocalDateTime current = LocalDateTime.now();
            order.setLastProcessing(current);

            orderLineItemRepository.save(orderLineItem);
            orderRepository.save(order);
        }
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
