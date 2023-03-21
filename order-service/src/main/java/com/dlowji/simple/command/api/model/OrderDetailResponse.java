package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {
    private String orderId;
    private String accountName;
    private String tableName;
    private Integer capacity;
    private List<OrderLineItemResponse> orderLineItemResponseList;
    private OrderStatus orderStatus;
    private LocalDateTime lastProcessing;
    //total price of the order items
    private BigDecimal subTotal;
    //total discount of the order items
    private BigDecimal itemDiscount;
    //the tax on the order items
    private BigDecimal tax;
    //total = subTotal+tax
    private BigDecimal total;
    private String promoCode;
    private BigDecimal discount;
    //grandTotal = total - itemDiscount - discount
    private BigDecimal grandTotal;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
