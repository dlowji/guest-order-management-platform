package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAccountCommand {
    @TargetAggregateIdentifier
    private String accountId;
    private String username;
    private String password;
    private String employeeId;

}
