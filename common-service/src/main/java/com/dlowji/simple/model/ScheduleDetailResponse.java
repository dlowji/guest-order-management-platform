package com.dlowji.simple.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDetailResponse {
    private String scheduleId;
    private LocalTime startWorkHour;
    private LocalTime endWorkHour;
    private LocalDate workDate;
    private String employeeId;
}
