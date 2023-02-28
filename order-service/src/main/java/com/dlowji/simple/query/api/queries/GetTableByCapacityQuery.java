package com.dlowji.simple.query.api.queries;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTableByCapacityQuery extends GetTablesQuery {
    private Integer capacity;
}
