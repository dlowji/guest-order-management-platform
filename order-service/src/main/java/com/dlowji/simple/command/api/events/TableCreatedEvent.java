package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.enums.TableStatus;
import lombok.Data;

@Data
public class TableCreatedEvent {
    private String tableId;
    private String code;
    private TableStatus tableStatus;
    private int capacity;
}
