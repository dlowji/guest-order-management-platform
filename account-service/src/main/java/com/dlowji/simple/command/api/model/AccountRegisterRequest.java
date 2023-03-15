package com.dlowji.simple.command.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisterRequest {
    @NotNull(message = "Please enter username")
    @NotEmpty(message = "Please enter a valid username")
    @Length(min = 8, max = 32, message = "Username has length between 8 and 32")
    private String username;
    @NotNull(message = "Please enter password")
    @NotEmpty(message = "Please enter a valid password")
    @Length(min = 8, message = "Password has length greater than 8")
    private String password;
    @NotNull(message = "Please enter full name")
    @NotEmpty(message = "Please enter full name")
    private String fullName;
    @NotNull(message = "Please enter email")
    @NotEmpty(message = "Please enter email")
    @Email(message = "Please enter valid email", regexp = "^[\\w!#$" +
            "%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")
    private String email;
    @NotNull
    private boolean gender;
    @NotNull
    @Min(value = 1000, message = "Salary must be greater than 1000, are you exploiting the surplus value of worker??")
    private BigDecimal salary;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    @NotNull(message = "Please enter phone number")
    @NotEmpty(message = "Please enter phone number")
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})", message = "Phone number" +
            " is not valid")
    private String phone;

    @NotNull(message = "Please enter address")
    @NotEmpty(message = "Please enter address")
    private String address;
    @NotNull(message = "Please enter role id")
    @NotEmpty(message = "Please enter a valid role id")
    private String roleId;
}
