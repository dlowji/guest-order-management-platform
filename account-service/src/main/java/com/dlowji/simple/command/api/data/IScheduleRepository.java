package com.dlowji.simple.command.api.data;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IScheduleRepository extends JpaRepository<Schedule, String> {
    List<Schedule> findByEmployee(Employee employee, Sort createdAt);
}
