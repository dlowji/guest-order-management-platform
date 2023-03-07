package com.dlowji.simple.query.api.queries;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTablesByStatusQuery {
    private String status;
}
