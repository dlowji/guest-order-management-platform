package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IEmployeeRepository extends JpaRepository<Employee, String> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}
