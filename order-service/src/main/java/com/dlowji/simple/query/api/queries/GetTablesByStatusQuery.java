package com.dlowji.simple.query.api.queries;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTablesByStatusQuery extends GetTablesQuery {
    private String status;
}
