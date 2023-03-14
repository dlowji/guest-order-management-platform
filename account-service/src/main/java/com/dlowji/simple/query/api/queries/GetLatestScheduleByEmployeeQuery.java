package com.dlowji.simple.query.api.queries;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GetLatestScheduleByEmployeeQuery {
    private String employeeId;
}
