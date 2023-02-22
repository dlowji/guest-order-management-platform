package com.dlowji.simple.command.api.aggregates;

import com.dlowji.simple.command.api.commands.CreateAccountCommand;
import com.dlowji.simple.command.api.events.account.AccountCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private String username;
    private String employeeId;

    public AccountAggregate() {

    }

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        //encrypt password
        String hashedPwd = createAccountCommand.getPassword();

        AccountCreatedEvent accountCreatedEvent = AccountCreatedEvent.builder()
                .accountId(createAccountCommand.getAccountId())
                .username(createAccountCommand.getUsername())
                .password(hashedPwd)
                .employeeId(createAccountCommand.getEmployeeId())
                .build();

        AggregateLifecycle.apply(accountCreatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountId = accountCreatedEvent.getAccountId();
        this.username = accountCreatedEvent.getUsername();
        this.employeeId = accountCreatedEvent.getEmployeeId();
    }
}
