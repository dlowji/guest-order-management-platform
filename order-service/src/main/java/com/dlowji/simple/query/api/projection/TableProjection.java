package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.ITableRepository;
import com.dlowji.simple.command.api.data.SeveredTable;
import com.dlowji.simple.command.api.model.TableResponse;
import com.dlowji.simple.query.api.queries.GetTableByCapacityQuery;
import com.dlowji.simple.query.api.queries.GetTablesByStatusQuery;
import com.dlowji.simple.query.api.queries.GetTableByIdQuery;
import com.dlowji.simple.query.api.queries.GetTablesQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TableProjection {
    private final ITableRepository tableRepository;

    public TableProjection(ITableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    @QueryHandler
    public List<TableResponse> handle(GetTablesQuery getTablesQuery) {
        List<SeveredTable> tables = tableRepository.findAll();

        return tables.stream().map(this::mapToTableResponse).toList();
    }

    @QueryHandler
    public List<TableResponse> handle(GetTableByCapacityQuery getTableByCapacityQuery) {
        int capacity = getTableByCapacityQuery.getCapacity();
        return tableRepository.findAllByCapacity(capacity).stream().map(this::mapToTableResponse).toList();
    }

    @QueryHandler
    public List<TableResponse> handle(GetTablesByStatusQuery getTableByStatusQuery) {
        String status = getTableByStatusQuery.getStatus();
        return tableRepository.findAllByTableStatus(status).stream().map(this::mapToTableResponse).toList();
    }

    @QueryHandler
    public TableResponse handle(GetTableByIdQuery getTableQuery) {
        String tableId = getTableQuery.getTableId();
        Optional<SeveredTable> existTable = tableRepository.findById(tableId);

        return existTable.map(this::mapToTableResponse).orElse(null);
    }

    private TableResponse mapToTableResponse(SeveredTable severedTable) {
        return TableResponse.builder()
                .tableId(severedTable.getTableId())
                .code(severedTable.getCode())
                .tableStatus(severedTable.getTableStatus())
                .capacity(severedTable.getCapacity())
                .createdAt(severedTable.getCreatedAt())
                .updatedAt(severedTable.getUpdatedAt())
                .build();
    }
}
