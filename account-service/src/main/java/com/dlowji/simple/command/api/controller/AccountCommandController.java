package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.commands.CreateAccountCommand;
import com.dlowji.simple.command.api.commands.RequestCreateAccountCommand;
import com.dlowji.simple.command.api.commands.RequestCreateEmployeeCommand;
import com.dlowji.simple.command.api.model.AccountRequest;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountCommandController {
    private final CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String create(@RequestBody AccountRequest accountRequest) {
        try {
            String employeeId = UUID.randomUUID().toString();

            RequestCreateEmployeeCommand requestCreateEmployeeCommand = RequestCreateEmployeeCommand.builder()
                    .employeeId(employeeId)
                    .username(accountRequest.getUsername())
                    .password(accountRequest.getPassword())
                    .fullName(accountRequest.getFullName())
                    .email(accountRequest.getEmail())
                    .gender(accountRequest.isGender())
                    .salary(accountRequest.getSalary())
                    .dob(accountRequest.getDob())
                    .address(accountRequest.getAddress())
                    .phone(accountRequest.getPhone())
                    .roleId(accountRequest.getRoleId())
                    .build();

            return commandGateway.sendAndWait(requestCreateEmployeeCommand);
        } catch (CommandExecutionException exception) {
            return "";
        }
    }
}
