package com.dlowji.simple.command.api.aggregates;

import com.dlowji.simple.command.api.commands.CreateEmployeeCommand;
import com.dlowji.simple.command.api.events.employee.EmployeeCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

@Aggregate
public class EmployeeAggregate {
    @AggregateIdentifier
    private String employeeId;
    private String fullName;
    private String email;
    private boolean gender;
    private BigDecimal salary;
    private LocalDate dob;
    private String phone;
    private String address;
    private String roleId;

    @CommandHandler
    public EmployeeAggregate(CreateEmployeeCommand createEmployeeCommand) {
        EmployeeCreatedEvent employeeCreatedEvent = new EmployeeCreatedEvent();
        BeanUtils.copyProperties(createEmployeeCommand, employeeCreatedEvent);
        AggregateLifecycle.apply(employeeCreatedEvent);
    }

    @EventSourcingHandler
    public void on(EmployeeCreatedEvent employeeCreatedEvent) {
        this.employeeId = employeeCreatedEvent.getEmployeeId();
        this.fullName = employeeCreatedEvent.getFullName();
        this.email = employeeCreatedEvent.getEmail();
        this.gender = employeeCreatedEvent.isGender();
        this.salary = employeeCreatedEvent.getSalary();
        this.dob = employeeCreatedEvent.getDob();
        this.phone = employeeCreatedEvent.getPhone();
        this.address = employeeCreatedEvent.getAddress();
        this.roleId = employeeCreatedEvent.getRoleId();
    }
}
