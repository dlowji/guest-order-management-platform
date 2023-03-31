package com.dlowji.simple.command.api.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DishDTO {
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String categoryId;
}
