package com.dlowji.simple.query.api.queries;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTableByIdQuery extends GetTablesQuery {
    private String tableId;
}
