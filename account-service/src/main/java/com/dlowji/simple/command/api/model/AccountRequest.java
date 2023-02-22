package com.dlowji.simple.command.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRequest {
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
