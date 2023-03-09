package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableRequest {
    @NotNull(message = "Please enter table code")
    @NotEmpty(message = "Please enter a valid table code")
    private String code;
    @NotNull(message = "Please enter capacity of table")
    @NotEmpty(message = "Please enter a valid capacity")
    private int capacity;
}
