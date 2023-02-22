package com.dlowji.simple.command.api.events.account;

import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.Employee;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Optional;

@Component
@ProcessingGroup("account")
public class AccountEventsHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(AccountEventsHandler.class);

    private final IAccountRepository accountRepository;
    private final IEmployeeRepository employeeRepository;

    public AccountEventsHandler(IAccountRepository accountRepository, IEmployeeRepository employeeRepository) {
        this.accountRepository = accountRepository;
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        LOGGER.info("AccountCreatedEvent in Handler for employee Id: {}", accountCreatedEvent.getEmployeeId());
        String employeeId = accountCreatedEvent.getEmployeeId();
        Optional<Employee> existEmployee = employeeRepository.findById(employeeId);

        if (existEmployee.isPresent()) {
            Employee employee = existEmployee.get();
            Account account = Account.builder()
                    .accountId(accountCreatedEvent.getAccountId())
                    .username(accountCreatedEvent.getUsername())
                    .password(accountCreatedEvent.getPassword())
                    .lastLogin(ZonedDateTime.now())
                    .employee(employee)
                    .build();
            accountRepository.save(account);
        }
    }
}
