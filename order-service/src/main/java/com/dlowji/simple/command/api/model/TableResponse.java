package com.dlowji.simple.command.api.model;

import com.dlowji.simple.command.api.enums.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableResponse {
    private String tableId;
    private String code;
    private TableStatus tableStatus;
    private Integer capacity;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
}
