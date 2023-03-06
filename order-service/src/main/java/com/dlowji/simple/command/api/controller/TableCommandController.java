package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.model.TableRequest;
import com.dlowji.simple.command.api.services.TableCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tables")
public class TableCommandController {

    private final TableCommandService tableCommandService;

    public TableCommandController(TableCommandService tableCommandService) {
        this.tableCommandService = tableCommandService;
    }

    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody TableRequest tableRequest) {
        return tableCommandService.createTable(tableRequest);
    }
}
