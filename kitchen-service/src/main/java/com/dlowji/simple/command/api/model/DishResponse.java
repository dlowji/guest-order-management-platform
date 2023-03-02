package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.DishStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
@Data
public class DishResponse {
    private String dishId;
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
