package com.dlowji.simple.command.api.events;

import com.dlowji.simple.command.api.data.ITableRepository;
import com.dlowji.simple.command.api.data.SeveredTable;
import com.dlowji.simple.command.api.enums.TableStatus;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class TableEventsHandler {

    private final ITableRepository tableRepository;

    public TableEventsHandler(ITableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @EventHandler
    public void on(TableCreatedEvent tableCreatedEvent) {
        SeveredTable table = SeveredTable.builder()
                .tableId(tableCreatedEvent.getTableId())
                .tableStatus(tableCreatedEvent.getTableStatus())
                .code(tableCreatedEvent.getCode())
                .capacity(tableCreatedEvent.getCapacity())
                .build();
        System.out.println("inside handler create table");
        tableRepository.save(table);
    }
}
