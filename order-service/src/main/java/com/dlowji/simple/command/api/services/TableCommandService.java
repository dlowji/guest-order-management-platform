package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateTableCommand;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.TableRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TableCommandService {

    private final CommandGateway commandGateway;

    public TableCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public ResponseEntity<?> createTable(TableRequest tableRequest) {
        String tableId = UUID.randomUUID().toString();
        System.out.println(tableRequest.getCode());
        System.out.println(tableRequest.getCapacity());
        CreateTableCommand createTableCommand = CreateTableCommand.builder()
                .tableId(tableId)
                .code(tableRequest.getCode())
                .tableStatus(TableStatus.FREE)
                .capacity(tableRequest.getCapacity())
                .build();

        try {
            commandGateway.send(createTableCommand);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Create severed table successfully");
            response.put("tableId", tableId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating severed: " + e.getMessage());
        }
    }
}
