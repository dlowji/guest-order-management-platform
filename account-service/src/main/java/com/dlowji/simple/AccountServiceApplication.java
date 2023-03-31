package com.dlowji.simple;

import com.dlowji.simple.command.api.data.IAccountRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.data.Role;
import org.springframework.beans.factory.annotation.Autowired;
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
	class setupRole implements CommandLineRunner {
		@Autowired
		private IRoleRepository iRoleRepository;

		@Override
		public void run(String... args) throws Exception {
			try {
				if (!iRoleRepository.findById("R01").isPresent()) {
					Role adminRole = Role.builder().roleId("R01").roleName("Admin").description("Super power role, the role can " + "defeat anyone like @Dlowji").build();
					iRoleRepository.save(adminRole);
				}

				if (!iRoleRepository.findById("RO2").isPresent()) {
					Role employeeRole = Role.builder().roleId("R02").roleName("Employee").description("Chicken role").build();
					iRoleRepository.save(employeeRole);
				}

				if (!iRoleRepository.findById("R03").isPresent()) {
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
	class setupAccount implements CommandLineRunner {
		@Autowired
		private IAccountRepository iAccountRepository;

		@Override
		public void run(String... args) throws Exception {
			try {
			}
			catch (Exception e) {

			}

		}
	}
}
