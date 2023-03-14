package com.dlowji.simple.command.api.events.schedule;

import com.dlowji.simple.command.api.data.Employee;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import com.dlowji.simple.command.api.data.IScheduleRepository;
import com.dlowji.simple.command.api.data.Schedule;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScheduleEventsHandler {

    private final IScheduleRepository scheduleRepository;
    private final IEmployeeRepository employeeRepository;

    public ScheduleEventsHandler(IScheduleRepository scheduleRepository, IEmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
    }

    @EventHandler
    public void on(ScheduleCreatedEvent scheduleCreatedEvent) {
        String employeeId = scheduleCreatedEvent.getEmployeeId();
        Optional<Employee> existEmployee = employeeRepository.findById(employeeId);
        if (existEmployee.isPresent()) {
            Employee employee = existEmployee.get();
            Schedule schedule = Schedule.builder()
                    .scheduleId(scheduleCreatedEvent.getScheduleId())
                    .startWorkHour(scheduleCreatedEvent.getStartWorkHour())
                    .endWorkHour(scheduleCreatedEvent.getEndWorkHour())
                    .workDate(scheduleCreatedEvent.getWorkDate())
                    .employee(employee)
                    .build();
            scheduleRepository.save(schedule);
        }
    }

    @EventHandler
    public void on(ScheduleUpdatedEvent scheduleUpdatedEvent) {
        String scheduleId = scheduleUpdatedEvent.getScheduleId();
        Optional<Schedule> existSchedule = scheduleRepository.findById(scheduleId);
        System.out.println("update schedule");
        System.out.println(existSchedule.isPresent());
        System.out.println(scheduleUpdatedEvent.getEndWorkHour());
        if (existSchedule.isPresent()) {
            Schedule schedule = existSchedule.get();
            schedule.setEndWorkHour(scheduleUpdatedEvent.getEndWorkHour());
            scheduleRepository.save(schedule);
        }
    }
}
