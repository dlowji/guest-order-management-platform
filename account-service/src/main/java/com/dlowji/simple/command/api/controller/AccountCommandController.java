package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.commands.RequestCreateEmployeeCommand;
import com.dlowji.simple.command.api.model.AccountLoginRequest;
import com.dlowji.simple.command.api.model.AccountRequest;
import com.dlowji.simple.command.api.service.AuthService;
import com.dlowji.simple.command.api.util.JwtUtil;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountCommandController {
    private final CommandGateway commandGateway;
    private final AuthService authService;

    public AccountCommandController(CommandGateway commandGateway, AuthService authService) {
        this.commandGateway = commandGateway;
        this.authService = authService;
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AccountLoginRequest accountLoginRequest) {
        return authService.login(accountLoginRequest);
    }
    @PostMapping("/auth/register")
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
