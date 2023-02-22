package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestCreateAccountCommand {
    private String accountId;
    private String username;
    private String password;
    private String employeeId;
}
