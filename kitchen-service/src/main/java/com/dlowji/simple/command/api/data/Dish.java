package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.DishStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "t_dish")
public class Dish {
    @Id
    private String dishId;
    private String title;
    private BigDecimal price;
    private String summary;
    private DishStatus dishStatus;

}
