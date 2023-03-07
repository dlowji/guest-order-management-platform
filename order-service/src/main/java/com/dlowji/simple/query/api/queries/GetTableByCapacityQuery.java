package com.dlowji.simple.query.api.queries;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTableByCapacityQuery{
    private Integer capacity;
}
