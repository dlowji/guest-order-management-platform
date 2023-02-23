package com.dlowji.simple.command.api.events.employee;

import com.dlowji.simple.command.api.data.Employee;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import com.dlowji.simple.command.api.data.IRoleRepository;
import com.dlowji.simple.command.api.data.Role;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ProcessingGroup("employee")
public class EmployeeEventsHandler {
    private final IEmployeeRepository employeeRepository;
    private final IRoleRepository roleRepository;

    public EmployeeEventsHandler(IEmployeeRepository employeeRepository, IRoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
    }

    @EventHandler
    public void on(EmployeeCreatedEvent employeeCreatedEvent) {
        String roleId = employeeCreatedEvent.getRoleId();
        Optional<Role> existRole = roleRepository.findById(roleId);

        if (existRole.isPresent()) {
            Role role = existRole.get();

            Employee employee = Employee.builder()
                    .employeeId(employeeCreatedEvent.getEmployeeId())
                    .fullName(employeeCreatedEvent.getFullName())
                    .email(employeeCreatedEvent.getEmail())
                    .gender(employeeCreatedEvent.isGender())
                    .dob(employeeCreatedEvent.getDob())
                    .salary(employeeCreatedEvent.getSalary())
                    .phone(employeeCreatedEvent.getPhone())
                    .address(employeeCreatedEvent.getAddress())
                    .role(role)
                    .build();
            employeeRepository.save(employee);
        }
    }
}

