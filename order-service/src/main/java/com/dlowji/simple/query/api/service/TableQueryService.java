package com.dlowji.simple.query.api.service;

import com.dlowji.simple.command.api.model.TableResponse;
import com.dlowji.simple.query.api.queries.GetTableByCapacityQuery;
import com.dlowji.simple.query.api.queries.GetTableByIdQuery;
import com.dlowji.simple.query.api.queries.GetTablesByStatusQuery;
import com.dlowji.simple.query.api.queries.GetTablesQuery;
import com.dlowji.simple.utils.NumberUtils;
import com.dlowji.simple.utils.StringUtils;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TableQueryService {
    private final QueryGateway queryGateway;

    public TableQueryService(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    public List<TableResponse> getTablesByProperties(Map<String, String> queryParams) {

        GetTablesQuery tableQuery = new GetTablesQuery();

        if (queryParams.isEmpty()) {
            return queryGateway.query(tableQuery, ResponseTypes.multipleInstancesOf(TableResponse.class)).join();
        }

        String status = queryParams.get("status");
        if (!StringUtils.isBlankString(status)) {
            tableQuery = GetTablesByStatusQuery.builder()
                    .status(status)
                    .build();
        }
        String capacity = queryParams.get("capacity");
        if (NumberUtils.isInteger(capacity)) {
            tableQuery = GetTableByCapacityQuery.builder()
                    .capacity(Integer.parseInt(capacity))
                    .build();
        }

        return queryGateway.query(tableQuery, ResponseTypes.multipleInstancesOf(TableResponse.class)).join();
    }

    public TableResponse getTableById(String tableId) {
        GetTableByIdQuery getTableByIdQuery = GetTableByIdQuery.builder().tableId(tableId).build();
        return queryGateway.query(getTableByIdQuery, ResponseTypes.instanceOf(TableResponse.class)).join();
    }
}
