package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.commands.CreateEmployeeCommand;
import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.model.AccountLoginRequest;
import com.dlowji.simple.command.api.model.AccountRegisterRequest;
import com.dlowji.simple.command.api.util.JwtUtil;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;
    private final CommandGateway commandGateway;
    private final JwtUtil jwtUtil;

    public AuthService(IAccountRepository accountRepository, IRoleRepository roleRepository, CommandGateway commandGateway, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.commandGateway = commandGateway;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(AccountLoginRequest accountLoginRequest) {
        Map<String, Object> response = new HashMap<>();
        //decode password
        String username = accountLoginRequest.getUsername();
        boolean isExistUsername = accountRepository.existsByUsername(username);
        if (!isExistUsername) {
            response.put("code", 500);
            response.put("message", "Username does not register");
            return ResponseEntity.badRequest().body(response);
        }
        Account account = accountRepository.findByUsername(username);
        boolean match = account.getPassword().equals(accountLoginRequest.getPassword());
        if (match) {
            String token = jwtUtil.generateToken(username);
            response.put("code", 0);
            response.put("message", "Login success");
            response.put("access_token", token);
            return ResponseEntity.ok(response);
        }

        response.put("code", 500);
        response.put("message", "Wrong password");
        return ResponseEntity.badRequest().body(response);
    }

    public ResponseEntity<?> register(AccountRegisterRequest accountRequest) {
        Map<String, Object> response = new HashMap<>();
        String username = accountRequest.getUsername();
        String hashedPwd = accountRequest.getPassword();
        boolean existUsername = accountRepository.existsByUsername(username);
        if (existUsername) {
            response.put("code", 510);
            response.put("message", "Username has already exist");
            return ResponseEntity.badRequest().body(response);
        }

        String roleId = accountRequest.getRoleId();
        boolean existRole = roleRepository.existsById(roleId);

        if (!existRole) {
            response.put("code", 512);
            response.put("message", "Role does not exist");
            return ResponseEntity.badRequest().body(response);
        }

        String employeeId = UUID.randomUUID().toString();

        CreateEmployeeCommand createEmployeeCommand = CreateEmployeeCommand.builder()
                .employeeId(employeeId)
                .username(accountRequest.getUsername())
                .password(hashedPwd)
                .fullName(accountRequest.getFullName())
                .email(accountRequest.getEmail())
                .gender(accountRequest.isGender())
                .salary(accountRequest.getSalary())
                .dob(accountRequest.getDob())
                .address(accountRequest.getAddress())
                .phone(accountRequest.getPhone())
                .roleId(accountRequest.getRoleId())
                .build();
        try {
            commandGateway.send(createEmployeeCommand);
            response.put("code", 0);
            response.put("message", "Register employee successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 511);
            response.put("message", "Error registering employee: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
