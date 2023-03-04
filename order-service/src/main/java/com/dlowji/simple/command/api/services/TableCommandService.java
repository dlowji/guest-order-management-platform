package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateTableCommand;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.TableRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class TableCommandService {

    private final CommandGateway commandGateway;

    public TableCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createTable(TableRequest tableRequest) {
        String tableId = UUID.randomUUID().toString();
        System.out.println(tableRequest.getCode());
        System.out.println(tableRequest.getCapacity());
        CreateTableCommand createTableCommand = CreateTableCommand.builder()
                .tableId(tableId)
                .code(tableRequest.getCode())
                .tableStatus(TableStatus.FREE)
                .capacity(tableRequest.getCapacity())
                .build();

        return commandGateway.send(createTableCommand);
    }
}
