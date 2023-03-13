package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest {
    @NotNull(message = "Please enter category id")
    @NotEmpty(message = "Please enter a valid category id")
    private String categoryId;
    @NotNull(message = "Please enter category name")
    @NotEmpty(message = "Please enter a valid category name")
    private String categoryName;
    @NotNull(message = "Please enter category icon")
    @NotEmpty(message = "Please enter a valid category icon")
    private String icon;
    @NotNull(message = "Please enter category link")
    @NotEmpty(message = "Please enter a valid category link")
    private String link;
}
