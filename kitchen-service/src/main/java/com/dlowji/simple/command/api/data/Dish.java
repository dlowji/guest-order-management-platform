package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.enums.DishStatus;
import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_dish")
public class Dish extends TimeStamp {
    @Id
    private String dishId;
    private String title;
    private String image;
    private BigDecimal price;
    private String summary;
    private String dishStatus;
    @ManyToOne
    private Category category;
}
