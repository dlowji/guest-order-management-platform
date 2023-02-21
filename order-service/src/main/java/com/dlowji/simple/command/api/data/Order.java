package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="t_orders")
public class Order {
    @Id
    private String orderId;
    private String userId;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItemList;

    public void addLineItem(OrderLineItem orderLineItem) {
        if (orderLineItemList == null) {
            orderLineItemList = new ArrayList<>();
        }
        this.orderLineItemList.add(orderLineItem);
    }

    public void removeLineItem(OrderLineItem orderLineItem) {
        if (orderLineItemList.size() == 0) {
            return;
        }
        this.orderLineItemList.remove(orderLineItem);
    }
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
