package com.dlowji.simple.query.api.queries;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTableByIdQuery{
    private String tableId;
}
