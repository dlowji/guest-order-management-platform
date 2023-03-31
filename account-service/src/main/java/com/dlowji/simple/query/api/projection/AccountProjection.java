package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IScheduleRepository;
import com.dlowji.simple.command.api.data.Schedule;
import com.dlowji.simple.model.AccountResponse;
import com.dlowji.simple.queries.GetAccountByIdQuery;
import com.dlowji.simple.query.api.queries.GetAccountByUsernameQuery;
import com.dlowji.simple.query.api.queries.GetAccountsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AccountProjection {
    private final IAccountRepository accountRepository;
    private final IScheduleRepository scheduleRepository;

    public AccountProjection(IAccountRepository accountRepository, IScheduleRepository scheduleRepository) {
        this.accountRepository = accountRepository;
        this.scheduleRepository = scheduleRepository;
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
        System.out.println(existAccount);
        if (existAccount == null) {
            return null;
        }

        return mapToAccountResponse(existAccount);
    }

    private AccountResponse mapToAccountResponse(Account account) {
        List<Schedule> scheduleList = scheduleRepository.findByEmployee(account.getEmployee(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Schedule schedule = scheduleList.get(0);
        return AccountResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .employeeId(account.getEmployee().getEmployeeId())
                .roleName(account.getEmployee().getRole().getRoleName())
                .scheduleId(schedule.getScheduleId())
                .build();
    }
}
