package com.dlowji.simple.command.api.data;

import com.dlowji.simple.converters.OrderStatusConverter;
import com.dlowji.simple.data.TimeStamp;
import com.dlowji.simple.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_orders")
public class Order extends TimeStamp {
    @Id
    private String orderId;
    private String accountId;
    private String tableId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId", orphanRemoval = true)
    private List<OrderLineItem> orderLineItemList = new ArrayList<>();

    @Column(name = "ORDER_STATUS")
    @Convert(converter = OrderStatusConverter.class)
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
}
