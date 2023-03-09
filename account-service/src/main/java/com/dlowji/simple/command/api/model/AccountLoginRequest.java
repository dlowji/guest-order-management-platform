package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountLoginRequest {
    @NotNull(message = "Please enter username")
    @NotEmpty(message = "Please enter a valid username")
    @Length(min = 8, max = 32, message = "Username has length between 8 and 32")
    private String username;
    @NotNull(message = "Please enter password")
    @NotEmpty(message = "Please enter a valid password")
    @Length(min = 8, message = "Password has length greater than 8")
    private String password;
}
