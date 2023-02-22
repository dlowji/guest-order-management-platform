package com.dlowji.simple.command.api.data;

import jakarta.persistence.*;

@Entity
@Table(name = "t_ingredient")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ingredientID;
    private String title;
    private Integer quantity;
    private String unit;
}
