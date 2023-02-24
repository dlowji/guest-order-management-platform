package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.model.AccountResponse;
import com.dlowji.simple.query.api.queries.GetAccountsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountProjection {
    private final IAccountRepository accountRepository;

    public AccountProjection(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @QueryHandler
    public List<AccountResponse> handle(GetAccountsQuery getAccountsQuery) {
        List<Account> accounts = accountRepository.findAll();

        return accounts.stream().map(this::mapToAccountResponse).toList();
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .password(account.getPassword())
                .employeeId(account.getEmployee().getEmployeeId())
                .roleId(account.getEmployee().getRole().getRoleId())
                .build();
    }
}
