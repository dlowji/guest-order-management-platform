package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.model.AccountResponse;
import com.dlowji.simple.query.api.queries.GetAccountByIdQuery;
import com.dlowji.simple.query.api.queries.GetAccountByUsernameQuery;
import com.dlowji.simple.query.api.queries.GetAccountsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    @QueryHandler
    public AccountResponse handle(GetAccountByIdQuery getAccountByIdQuery) {
        String accountId = getAccountByIdQuery.getAccountId();
        if (accountId == null) {
            return null;
        }
        Optional<Account> existAccount = accountRepository.findById(accountId);
        return existAccount.map(this::mapToAccountResponse).orElse(null);
    }

    @QueryHandler
    public AccountResponse handle(GetAccountByUsernameQuery getAccountByUsernameQuery) {
        String username = getAccountByUsernameQuery.getUsername();
        if (username == null) {
            return null;
        }
        Account existAccount = accountRepository.findByUsername(username);
        if (existAccount == null) {
            return null;
        }

        return mapToAccountResponse(existAccount);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .employeeId(account.getEmployee().getEmployeeId())
                .roleName(account.getEmployee().getRole().getRoleName())
                .build();
    }
}
