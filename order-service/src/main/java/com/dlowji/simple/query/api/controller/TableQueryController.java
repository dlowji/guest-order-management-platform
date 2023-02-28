package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.TableResponse;
import com.dlowji.simple.query.api.service.TableQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tables")
public class TableQueryController {
    private final TableQueryService tableQueryService;

    public TableQueryController(TableQueryService tableQueryService) {
        this.tableQueryService = tableQueryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TableResponse> getAllTables(@RequestParam Map<String, String> queryParams) {
        return tableQueryService.getTablesByProperties(queryParams);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TableResponse getTableById(@PathVariable String id) {
        return tableQueryService.getTableById(id);
    }

}
