package com.dlowji.simple.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishResponse{
    private String dishId;
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;
    private String categoryName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
