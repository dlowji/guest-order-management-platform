package com.dlowji.simple.command.api.commands.handlers;

import com.dlowji.simple.command.api.commands.CreateEmployeeCommand;
import com.dlowji.simple.command.api.commands.RequestCreateEmployeeCommand;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class EmployeeCommandHandler {
    @CommandHandler
    public void on(RequestCreateEmployeeCommand requestCreateEmployeeCommand, CommandGateway commandGateway, IEmployeeRepository employeeRepository, IRoleRepository roleRepository) {
        if (employeeRepository.existsByEmail(requestCreateEmployeeCommand.getEmail())) {
            throw new IllegalStateException(String.format("Email: %s is already exists", requestCreateEmployeeCommand.getEmail()));
        }
        if (employeeRepository.existsByPhone(requestCreateEmployeeCommand.getPhone())) {
            throw new IllegalStateException(String.format("Phone: %s is already exists", requestCreateEmployeeCommand.getPhone()));
        }
//        if (!roleRepository.existsById(requestCreateEmployeeCommand.getRoleId())) {
//            throw new IllegalStateException(String.format("Role with id %s does not exist", requestCreateEmployeeCommand.getRoleId()));
//        }

        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();
        BeanUtils.copyProperties(requestCreateEmployeeCommand, createEmployeeCommand);
        commandGateway.send(createEmployeeCommand);
    }
}
