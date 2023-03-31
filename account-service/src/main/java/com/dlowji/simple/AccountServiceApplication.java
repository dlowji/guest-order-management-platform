package com.dlowji.simple;

import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.data.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
				if (iRoleRepository.findById("R01").isEmpty()) {
					Role adminRole = Role.builder().roleId("R01").roleName("Admin").description("Super power role, the role can " + "defeat anyone like @Dlowji").build();
					iRoleRepository.save(adminRole);
				}

				if (iRoleRepository.findById("RO2").isEmpty()) {
					Role employeeRole = Role.builder().roleId("R02").roleName("Employee").description("Chicken role").build();
					iRoleRepository.save(employeeRole);
				}

				if (iRoleRepository.findById("R03").isEmpty()) {
					Role chefRole = Role.builder().roleId("R03").roleName("Chef").description("Powerful role")
					.build();
					iRoleRepository.save(chefRole);
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

		public setupAccount(IAccountRepository iAccountRepository) {
			this.iAccountRepository = iAccountRepository;
		}

		@Override
		public void run(String... args) throws Exception {
			try {

			}
			catch (Exception e) {

			}

		}
	}
}
