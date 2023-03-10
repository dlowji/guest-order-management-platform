package com.dlowji.simple.command.api.events.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleCreatedEvent {
    private String scheduleId;
    private LocalTime startWorkHour;
    private LocalTime endWorkHour;
    private LocalDate workDate;
    private String employeeId;
}
