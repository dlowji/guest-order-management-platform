package com.dlowji.simple.command.api.commands.handlers;

import com.dlowji.simple.command.api.commands.CreateAccountCommand;
import com.dlowji.simple.command.api.commands.RequestCreateAccountCommand;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandHandler {
    @CommandHandler
    public void handle(RequestCreateAccountCommand requestCreateAccountCommand, CommandGateway commandGateway, IAccountRepository accountRepository, IEmployeeRepository employeeRepository) {
        if (accountRepository.existsByUsername(requestCreateAccountCommand.getUsername())) {
            throw new IllegalStateException(String.format("Account with username %s already exists", requestCreateAccountCommand.getUsername()));
        }
        if (employeeRepository.existsById(requestCreateAccountCommand.getEmployeeId())) {
            throw new IllegalStateException(String.format("Employee with id %s does not exist", requestCreateAccountCommand.getEmployeeId()));
        }
        CreateAccountCommand createAccountCommand = new CreateAccountCommand();
        BeanUtils.copyProperties(requestCreateAccountCommand, createAccountCommand);
        commandGateway.send(createAccountCommand);
    }
}
