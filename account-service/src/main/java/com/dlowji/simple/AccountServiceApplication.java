package com.dlowji.simple;

import com.dlowji.simple.command.api.data.*;
import com.dlowji.simple.command.api.model.dto.AccountDTO;
import com.dlowji.simple.command.api.model.dto.RoleDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class AccountServiceApplication {


	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Component
	@Order(1)
	static
	class setupRole implements CommandLineRunner {
		private final IRoleRepository iRoleRepository;

		public setupRole(IRoleRepository iRoleRepository) {
			this.iRoleRepository = iRoleRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			try {
				String path = "account-service/src/main/resources/roles.json";
				ObjectMapper objectMapper = new ObjectMapper();
				RoleDTO[] roles = objectMapper.readValue(new File(path), RoleDTO[].class);
				for (RoleDTO role : roles) {
					if (iRoleRepository.findById(role.getRoleId()).isEmpty()) {
						Role savedRole = Role.builder()
								.roleId(role.getRoleId())
								.roleName(role.getRoleName())
								.description(role.getRoleDescription())
								.build();
						iRoleRepository.save(savedRole);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Component
	@Order(2)
	static
	class setupAccount implements CommandLineRunner {
		private final IAccountRepository iAccountRepository;
		private final IEmployeeRepository employeeRepository;
		private final IRoleRepository roleRepository;

		public setupAccount(IAccountRepository iAccountRepository, IEmployeeRepository employeeRepository, IRoleRepository roleRepository) {
			this.iAccountRepository = iAccountRepository;
			this.employeeRepository = employeeRepository;
			this.roleRepository = roleRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			try {
				String path = "account-service/src/main/resources/accounts.json";
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.registerModule(new JavaTimeModule());
				objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
				AccountDTO[] accounts = objectMapper.readValue(new File(path), AccountDTO[].class);
				for (AccountDTO account : accounts) {
					String roleId = account.getRoleId();
					if (roleId != null) {
						Optional<Role> existRole = roleRepository.findById(roleId);
						if (existRole.isPresent()) {
							Role role = existRole.get();
							if (iAccountRepository.findByUsername(account.getUsername()) == null) {
								String employeeId = UUID.randomUUID().toString();
								Employee employee = Employee.builder()
										.employeeId(employeeId)
										.fullName(account.getFullName())
										.email(account.getEmail())
										.gender(account.isGender())
										.dob(account.getDob())
										.salary(account.getSalary())
										.phone(account.getPhone())
										.address(account.getAddress())
										.role(role)
										.build();
								employeeRepository.save(employee);
								String accountId = UUID.randomUUID().toString();
								Account savedAccount = Account.builder()
										.accountId(accountId)
										.username(account.getUsername())
										.password(account.getPassword())
										.lastLogin(ZonedDateTime.now())
										.employee(employee)
										.build();
								iAccountRepository.save(savedAccount);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
