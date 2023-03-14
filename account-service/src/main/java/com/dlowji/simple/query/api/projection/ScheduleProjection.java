package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.Employee;
import com.dlowji.simple.command.api.data.IEmployeeRepository;
import com.dlowji.simple.command.api.data.IScheduleRepository;
import com.dlowji.simple.command.api.data.Schedule;
import com.dlowji.simple.model.ScheduleDetailResponse;
import com.dlowji.simple.queries.GetScheduleDetailByIdQuery;
import com.dlowji.simple.query.api.queries.GetLatestScheduleByEmployeeQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ScheduleProjection {
    private final IScheduleRepository scheduleRepository;
    private final IEmployeeRepository employeeRepository;

    public ScheduleProjection(IScheduleRepository scheduleRepository, IEmployeeRepository employeeRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
    }

    @QueryHandler
    public ScheduleDetailResponse handle(GetScheduleDetailByIdQuery getScheduleDetailByIdQuery) {
        String scheduleId = getScheduleDetailByIdQuery.getScheduleId();
        Optional<Schedule> existSchedule = scheduleRepository.findById(scheduleId);
        return existSchedule.map(this::mapToScheduleDetailResponse).orElse(null);

    }

    @QueryHandler
    public ScheduleDetailResponse handle(GetLatestScheduleByEmployeeQuery getLatestScheduleByEmployeeQuery) {
        String employeeId = getLatestScheduleByEmployeeQuery.getEmployeeId();
        Optional<Employee> existEmployee = employeeRepository.findById(employeeId);
        if (existEmployee.isPresent()) {
            Employee employee = existEmployee.get();
            List<Schedule> scheduleList = scheduleRepository.findByEmployee(employee, Sort.by(Sort.Direction.DESC, "createdAt"));
            Schedule schedule = scheduleList.get(0);
            return mapToScheduleDetailResponse(schedule);
        }
        return null;
    }

    private ScheduleDetailResponse mapToScheduleDetailResponse(Schedule schedule) {
        return ScheduleDetailResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .startWorkHour(schedule.getStartWorkHour())
                .endWorkHour(schedule.getEndWorkHour())
                .workDate(schedule.getWorkDate())
                .employeeId(schedule.getEmployee().getEmployeeId())
                .build();
    }
}
