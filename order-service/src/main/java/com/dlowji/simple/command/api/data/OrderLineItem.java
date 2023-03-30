package com.dlowji.simple.command.api.data;

import com.dlowji.simple.converters.OrderLineItemStatusConverter;
import com.dlowji.simple.data.TimeStamp;
import com.dlowji.simple.enums.OrderLineItemStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_order_line_item")
public class OrderLineItem extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderId;
    private String dishId;
    private Integer quantity;
    private BigDecimal price;
    private String note;
    @Column(name = "ORDER_LINE_ITEM_STATUS")
    @Convert(converter = OrderLineItemStatusConverter.class)
    private OrderLineItemStatus orderLineItemStatus;
}
