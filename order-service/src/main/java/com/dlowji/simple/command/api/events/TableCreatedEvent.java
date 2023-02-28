package com.dlowji.simple.command.api.events;

import lombok.Data;

@Data
public class TableCreatedEvent {
    private String tableId;
    private String code;
    private String tableStatus;
    private Integer capacity;
}
