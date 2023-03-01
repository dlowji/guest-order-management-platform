package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.model.AccountLoginRequest;
import com.dlowji.simple.command.api.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final IAccountRepository accountRepository;
    private final JwtUtil jwtUtil;

    public AuthService(IAccountRepository accountRepository, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(AccountLoginRequest accountLoginRequest) {
        //decode password
        String username = accountLoginRequest.getUsername();
        boolean isExistUsername = accountRepository.existsByUsername(username);
        if (isExistUsername) {
            Account account = accountRepository.findByUsername(username);
            boolean match = account.getPassword().equals(accountLoginRequest.getPassword());
            if (match) {
                String token = jwtUtil.generateToken(username);
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
        }
        Map<String, Object> response = new HashMap<>();
        response.put("message", "wrong username or password");
        return ResponseEntity.ok(response);
    }
}
