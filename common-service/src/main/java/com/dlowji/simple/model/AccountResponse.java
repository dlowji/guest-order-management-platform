package com.dlowji.simple.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String accountId;
    private String username;
    private String employeeId;
    private String roleName;
    private String scheduleId;
}
