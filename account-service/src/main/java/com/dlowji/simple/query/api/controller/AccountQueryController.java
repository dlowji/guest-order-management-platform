package com.dlowji.simple.query.api.controller;

import com.dlowji.simple.command.api.model.AccountResponse;
import com.dlowji.simple.query.api.queries.GetAccountsQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountQueryController {
    private final QueryGateway queryGateway;


    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts() {
        GetAccountsQuery getAccountsQuery = new GetAccountsQuery();

        return queryGateway.query(getAccountsQuery, ResponseTypes.multipleInstancesOf(AccountResponse.class)).join();
    }
}
