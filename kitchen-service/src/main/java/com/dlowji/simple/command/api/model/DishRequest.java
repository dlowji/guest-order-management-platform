package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.DishStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DishRequest {
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
}
