package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountLogoutRequest {
    @NotNull(message = "Please enter account id")
    @NotEmpty(message = "Please enter a valid account id")
    private String accountId;
    @NotNull(message = "Please enter schedule id")
    @NotEmpty(message = "Please enter a valid schedule id")
    private String scheduleId;
}
