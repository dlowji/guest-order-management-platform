package com.dlowji.simple.command.api.events.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private String username;
    private String password;
    private String employeeId;
}
