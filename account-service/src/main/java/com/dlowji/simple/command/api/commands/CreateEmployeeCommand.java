package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class CreateEmployeeCommand {
    @TargetAggregateIdentifier
    private String employeeId;
    private String username;
    private String password;
    private String fullName;
    private String email;
    private boolean gender;
    private BigDecimal salary;
    private LocalDate dob;
    private String phone;
    private String address;
    private String roleId;
}
