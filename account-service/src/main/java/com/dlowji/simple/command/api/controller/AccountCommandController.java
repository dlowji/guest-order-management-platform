package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.data.Role;
import com.dlowji.simple.command.api.model.AccountLoginRequest;
import com.dlowji.simple.command.api.model.AccountRegisterRequest;
import com.dlowji.simple.command.api.model.RoleRequest;
import com.dlowji.simple.command.api.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountCommandController {
    private final AuthService authService;

    private final IRoleRepository roleRepository;

    public AccountCommandController(AuthService authService, IRoleRepository roleRepository) {
        this.authService = authService;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register/roles")
    public String addRole(@Valid @RequestBody RoleRequest roleRequest) {
        Role role = Role.builder()
                .roleId(roleRequest.getRoleId())
                .roleName(roleRequest.getRoleName())
                .description(roleRequest.getRoleDescription())
                .build();
        try {
            roleRepository.save(role);
            return "Success";
        } catch (Exception e) {
            return "False";
        }
    }
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody AccountLoginRequest accountLoginRequest) {
        return authService.login(accountLoginRequest);
    }
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@Valid @RequestBody AccountRegisterRequest accountRequest) {
        return authService.register(accountRequest);
    }
}
