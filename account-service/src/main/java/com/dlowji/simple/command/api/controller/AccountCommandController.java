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

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCommandController.class);
    private final CommandGateway commandGateway;

    public AccountCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/create")
    public String create(@RequestBody AccountRequest accountRequest) {
        LOGGER.debug("Create account");
        try {
            String employeeId = UUID.randomUUID().toString();
            RequestCreateEmployeeCommand requestCreateEmployeeCommand = RequestCreateEmployeeCommand.builder()
                    .employeeId(employeeId)
                    .fullName(accountRequest.getFullName())
                    .email(accountRequest.getEmail())
                    .gender(accountRequest.isGender())
                    .salary(accountRequest.getSalary())
                    .dob(accountRequest.getDob())
                    .address(accountRequest.getAddress())
                    .phone(accountRequest.getPhone())
                    .roleId(accountRequest.getRoleId())
                    .build();

            commandGateway.sendAndWait(requestCreateEmployeeCommand);

            String accountId = UUID.randomUUID().toString();
            RequestCreateAccountCommand requestCreateAccountCommand = RequestCreateAccountCommand.builder()
                    .accountId(accountId)
                    .username(accountRequest.getUsername())
                    .password(accountRequest.getPassword())
                    .employeeId(employeeId)
                    .build();
            return commandGateway.sendAndWait(requestCreateAccountCommand);
        } catch (CommandExecutionException exception) {
            LOGGER.warn("Create Account Command FAILED with message: {}", exception.getMessage());
            if (exception.getCause() != null) {
                LOGGER.warn("Caused by: {} {}", exception.getCause().getClass().getName(), exception.getCause().getMessage());
            }
            return "";
        }
    }
}
