package com.dlowji.simple.command.api.service;

import com.dlowji.simple.command.api.commands.CreateEmployeeCommand;
import com.dlowji.simple.command.api.commands.CreateScheduleCommand;
import com.dlowji.simple.command.api.commands.UpdateScheduleCommand;
import com.dlowji.simple.command.api.data.Account;
import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.data.IScheduleRepository;
import com.dlowji.simple.command.api.model.AccountLoginRequest;
import com.dlowji.simple.command.api.model.AccountRegisterRequest;
import com.dlowji.simple.command.api.util.JwtUtil;
import com.dlowji.simple.model.AccountResponse;
import com.dlowji.simple.model.ScheduleDetailResponse;
import com.dlowji.simple.query.api.queries.GetAccountByUsernameQuery;
import com.dlowji.simple.query.api.queries.GetLatestScheduleByEmployeeQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;
    private final IScheduleRepository scheduleRepository;
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;
    private final JwtUtil jwtUtil;

    public AuthService(IAccountRepository accountRepository, IRoleRepository roleRepository, IScheduleRepository scheduleRepository, CommandGateway commandGateway, QueryGateway queryGateway, JwtUtil jwtUtil) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.scheduleRepository = scheduleRepository;
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<?> login(AccountLoginRequest accountLoginRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
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
            boolean result = createSchedule(account.getEmployee().getEmployeeId());
            if (!result) {
                response.put("code", 550);
                response.put("message", "Error create work schedule");
                return ResponseEntity.internalServerError().body(response);
            }
            String token = jwtUtil.generateToken(username);
            response.put("code", 0);
            response.put("accountId", account.getAccountId());
            response.put("username", account.getUsername());
            response.put("role", account.getEmployee().getRole().getRoleName());
            response.put("access_token", token);
            response.put("token_type", "Bearer");
            return ResponseEntity.ok(response);
        }

        response.put("code", 500);
        response.put("message", "Wrong password");
        return ResponseEntity.badRequest().body(response);
    }

    private boolean createSchedule(String employeeId) {
        String scheduleId = UUID.randomUUID().toString();
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        CreateScheduleCommand createScheduleCommand = CreateScheduleCommand.builder()
                .scheduleId(scheduleId)
                .startWorkHour(currentTime)
                .endWorkHour(currentTime.plusHours(1))
                .workDate(currentDate)
                .employeeId(employeeId)
                .build();

        try {
            commandGateway.send(createScheduleCommand);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResponseEntity<?> register(AccountRegisterRequest accountRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
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
            String employeeId2 = commandGateway.sendAndWait(createEmployeeCommand);
            response.put("code", 0);
            response.put("message", "Register employee successfully");
            response.put("employeeId", employeeId2);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("code", 511);
            response.put("message", "Error registering employee: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<?> logout(String authorizationHeader) {
        Map<String, Object> response = new LinkedHashMap<>();
        String accountUsername = jwtUtil.getClaims(authorizationHeader.substring(7)).getSubject();
        GetAccountByUsernameQuery getAccountByUsernameQuery = GetAccountByUsernameQuery.builder()
                .username(accountUsername)
                .build();
        AccountResponse accountResponse = queryGateway.query(getAccountByUsernameQuery, ResponseTypes.instanceOf(AccountResponse.class)).join();
        if (accountResponse == null) {
            response.put("code", 100);
            response.put("message", "Account not exist");
            return ResponseEntity.badRequest().body(response);
        }

        boolean result = updateSchedule(accountResponse.getEmployeeId());

        if (result) {
            response.put("code", 0);
            response.put("message", "Logout successfully");
            return ResponseEntity.ok(response);
        }

        response.put("code", "550");
        response.put("message", "Error logout account");
        return ResponseEntity.internalServerError().body(response);
    }

    private boolean updateSchedule(String employeeId) {
        GetLatestScheduleByEmployeeQuery getLatestScheduleByEmployeeQuery = GetLatestScheduleByEmployeeQuery.builder()
                .employeeId(employeeId)
                .build();
        ScheduleDetailResponse scheduleDetailResponse = queryGateway.query(getLatestScheduleByEmployeeQuery, ResponseTypes.instanceOf(ScheduleDetailResponse.class)).join();

        if (scheduleDetailResponse == null) {
            return false;
        }

        String scheduleId = scheduleDetailResponse.getScheduleId();
        LocalTime currentTime = LocalTime.now();
        UpdateScheduleCommand updateScheduleCommand = UpdateScheduleCommand.builder()
                .scheduleId(scheduleId)
                .endWorkHour(currentTime)
                .build();

        try {
            commandGateway.send(updateScheduleCommand);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
