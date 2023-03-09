package com.dlowji.simple.command.api.saga;

import com.dlowji.simple.command.api.commands.CreateAccountCommand;
import com.dlowji.simple.command.api.events.account.AccountCreatedEvent;
import com.dlowji.simple.command.api.events.employee.EmployeeCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class AccountProcessingSAGA {
    private transient CommandGateway commandGateway;

    @Autowired
    public void setCommandGateway(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "employeeId")
    public void handle(EmployeeCreatedEvent employeeCreatedEvent) {
        String accountId = UUID.randomUUID().toString();
        //associate Saga
        SagaLifecycle.associateWith("accountId", accountId);
        commandGateway.send(CreateAccountCommand.builder()
                .accountId(accountId)
                .username(employeeCreatedEvent.getUsername())
                .password(employeeCreatedEvent.getPassword())
                .employeeId(employeeCreatedEvent.getEmployeeId())
                .build());
    }

    @SagaEventHandler(associationProperty = "accountId")
    public void handle(AccountCreatedEvent accountCreatedEvent) {
        System.out.println(accountCreatedEvent.getAccountId());
    }
}
