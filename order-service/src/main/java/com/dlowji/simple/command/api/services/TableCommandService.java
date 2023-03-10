package com.dlowji.simple.command.api.services;

import com.dlowji.simple.command.api.commands.CreateTableCommand;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.model.TableRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
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
        CreateTableCommand createTableCommand = CreateTableCommand.builder()
                .tableId(tableId)
                .code(tableRequest.getCode())
                .tableStatus(TableStatus.FREE)
                .capacity(tableRequest.getCapacity())
                .build();

        Map<String, Object> response = new LinkedHashMap<>();
        try {
            commandGateway.send(createTableCommand);
            response.put("code", 0);
            response.put("message", "Create severed table successfully");
            response.put("tableId", tableId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 502);
            response.put("message", "Error creating severed table: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
