package com.dlowji.simple.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableRequest {
    private String code;
    private Integer capacity;
    private String tableStatus;
}
