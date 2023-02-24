package com.dlowji.simple.command.api.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class DishResponse {
    private String dishId;
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;
}
