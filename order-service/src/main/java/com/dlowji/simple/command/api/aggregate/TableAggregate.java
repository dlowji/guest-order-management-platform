package com.dlowji.simple.command.api.aggregate;

import com.dlowji.simple.command.api.commands.CreateTableCommand;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.events.TableCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class TableAggregate {
    @AggregateIdentifier
    private String tableId;
    private String code;
    private int capacity;
    private TableStatus tableStatus;

    @CommandHandler
    public TableAggregate(CreateTableCommand createTableCommand) {
        TableCreatedEvent tableCreatedEvent = new TableCreatedEvent();
        BeanUtils.copyProperties(createTableCommand, tableCreatedEvent);
        System.out.println("inside aggregate");
        AggregateLifecycle.apply(tableCreatedEvent);
    }

    @EventSourcingHandler
    public void on(TableCreatedEvent tableCreatedEvent) {
        this.tableId = tableCreatedEvent.getTableId();
        this.code = tableCreatedEvent.getCode();
        this.capacity = tableCreatedEvent.getCapacity();
        this.tableStatus = TableStatus.FREE;
    }
}
