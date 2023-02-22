package com.dlowji.simple.command.api.data;

import jakarta.persistence.*;

@Entity
@Table(name = "t_recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;
    private String unit;
}
