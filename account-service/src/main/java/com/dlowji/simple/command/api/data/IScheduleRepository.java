package com.dlowji.simple.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IScheduleRepository extends JpaRepository<Schedule, String> {
}
