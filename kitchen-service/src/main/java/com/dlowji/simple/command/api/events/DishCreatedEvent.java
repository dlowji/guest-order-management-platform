package com.dlowji.simple.command.api.events;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishCreatedEvent {
    private String dishId;
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;
}
