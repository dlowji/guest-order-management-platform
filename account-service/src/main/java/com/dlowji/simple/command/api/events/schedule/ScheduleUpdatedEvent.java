package com.dlowji.simple.command.api.events.schedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleUpdatedEvent {
    private String scheduleId;
    private LocalTime endWorkHour;
}
