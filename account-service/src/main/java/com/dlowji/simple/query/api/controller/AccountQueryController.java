package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.AccountResponse;
import com.dlowji.simple.command.api.util.JwtUtil;
import com.dlowji.simple.query.api.queries.GetAccountByIdQuery;
import com.dlowji.simple.query.api.queries.GetAccountsQuery;
import io.jsonwebtoken.Claims;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountQueryController {
    private final QueryGateway queryGateway;
    private final JwtUtil jwtUtil;


    public AccountQueryController(QueryGateway queryGateway, JwtUtil jwtUtil) {
        this.queryGateway = queryGateway;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<?> getAllAccounts() {
        GetAccountsQuery getAccountsQuery = new GetAccountsQuery();
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            List<AccountResponse> accountResponseList = queryGateway.query(getAccountsQuery, ResponseTypes.multipleInstancesOf(AccountResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get all account successfully");
            response.put("data", accountResponseList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 600);
            response.put("message", "Error getting all account: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable String id) {
        GetAccountByIdQuery getAccountByIdQuery = GetAccountByIdQuery.builder()
                .accountId(id)
                .build();
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            AccountResponse accountResponse = queryGateway.query(getAccountByIdQuery, ResponseTypes.instanceOf(AccountResponse.class)).join();
            response.put("code", 0);
            response.put("message", "Get account by id successfully");
            response.put("data", accountResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 601);
            response.put("message", "Error getting account by id: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/getme")
    public ResponseEntity<?> getMe(@RequestHeader(value = "Authorization") String authorizationHeader) {
        System.out.println(authorizationHeader);
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            String token = authorizationHeader.substring(7);
            jwtUtil.validateToken(token);
            Claims claims = jwtUtil.getClaims(token);
            response.put("code", 0);
            response.put("message", "Get me successfully");
            response.put("data", claims);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 530);
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
