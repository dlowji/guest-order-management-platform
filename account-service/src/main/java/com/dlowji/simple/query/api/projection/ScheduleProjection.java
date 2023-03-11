package com.dlowji.simple.query.api.projection;

import com.dlowji.simple.command.api.data.IScheduleRepository;
import com.dlowji.simple.command.api.data.Schedule;
import com.dlowji.simple.model.ScheduleDetailResponse;
import com.dlowji.simple.queries.GetScheduleDetailByIdQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ScheduleProjection {
    private final IScheduleRepository scheduleRepository;

    public ScheduleProjection(IScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @QueryHandler
    public ScheduleDetailResponse handle(GetScheduleDetailByIdQuery getScheduleDetailByIdQuery) {
        String scheduleId = getScheduleDetailByIdQuery.getScheduleId();
        Optional<Schedule> existSchedule = scheduleRepository.findById(scheduleId);
        return existSchedule.map(this::mapToScheduleDetailResponse).orElse(null);

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
