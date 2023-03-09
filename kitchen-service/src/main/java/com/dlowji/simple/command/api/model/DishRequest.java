package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class DishRequest {
    @NotNull(message = "Please enter dish title")
    @NotEmpty(message = "Please enter a valid dish title")
    @Length(max = 32, message = "Dish title has length less than 32")
    private String title;
    @NotNull(message = "Please enter image address")
    @NotEmpty(message = "Please enter a valid image address")
    private String image;
    @NotNull(message = "Please enter dish price")
    @NotEmpty(message = "Please enter a valid dish price")
    private BigDecimal price;
    @NotNull(message = "Please enter dish summary")
    private String summary;
    @NotNull(message = "Please enter category id")
    @NotEmpty(message = "Please enter a valid category id")
    private String categoryId;
}
