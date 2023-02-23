package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_dish")
public class Dish extends TimeStamp {
    @Id
    private String dishId;
    private String title;
    private BigDecimal price;
    private String summary;
    private DishStatus dishStatus;
    @OneToOne
    private Recipe recipe;
}
