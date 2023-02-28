package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class OrderDetailResponse {
    private String orderId;
    private String userId;

    private OrderStatus orderStatus;
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
