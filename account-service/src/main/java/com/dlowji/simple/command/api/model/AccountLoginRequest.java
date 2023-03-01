package com.dlowji.simple.command.api.model;

import lombok.Data;

@Data
public class AccountLoginRequest {
    private String username;
    private String password;
}
