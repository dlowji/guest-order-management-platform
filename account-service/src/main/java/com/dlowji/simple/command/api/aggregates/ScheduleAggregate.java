package com.dlowji.simple.command.api.aggregates;

import com.dlowji.simple.command.api.commands.CreateScheduleCommand;
import com.dlowji.simple.command.api.commands.UpdateScheduleCommand;
import com.dlowji.simple.command.api.events.schedule.ScheduleCreatedEvent;
import com.dlowji.simple.command.api.events.schedule.ScheduleUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;
import java.time.LocalTime;

@Aggregate
public class ScheduleAggregate {
    @AggregateIdentifier
    private String scheduleId;
    private LocalTime startWorkHour;
    private LocalTime endWorkHour;
    private LocalDate workDate;
    private String employeeId;

    public ScheduleAggregate() {

    }

    @CommandHandler
    public void handle(CreateScheduleCommand scheduleCommand) {
        ScheduleCreatedEvent scheduleCreatedEvent = ScheduleCreatedEvent.builder()
                .scheduleId(scheduleCommand.getScheduleId())
                .startWorkHour(scheduleCommand.getStartWorkHour())
                .endWorkHour(scheduleCommand.getEndWorkHour())
                .workDate(scheduleCommand.getWorkDate())
                .employeeId(scheduleCommand.getEmployeeId())
                .build();

        AggregateLifecycle.apply(scheduleCreatedEvent);
    }

    public void handle(UpdateScheduleCommand updateScheduleCommand) {
        ScheduleUpdatedEvent scheduleUpdatedEvent = ScheduleUpdatedEvent.builder()
                .scheduleId(updateScheduleCommand.getScheduleId())
                .endWorkHour(updateScheduleCommand.getEndWorkHour())
                .build();

        AggregateLifecycle.apply(scheduleUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ScheduleCreatedEvent scheduleCreatedEvent) {
        this.scheduleId = scheduleCreatedEvent.getScheduleId();
        this.startWorkHour = scheduleCreatedEvent.getStartWorkHour();
        this.endWorkHour = scheduleCreatedEvent.getEndWorkHour();
        this.workDate = scheduleCreatedEvent.getWorkDate();
        this.employeeId = scheduleCreatedEvent.getEmployeeId();
    }

    @EventSourcingHandler
    public void on(ScheduleUpdatedEvent scheduleUpdatedEvent) {
        this.scheduleId = scheduleUpdatedEvent.getScheduleId();
        this.endWorkHour = scheduleUpdatedEvent.getEndWorkHour();
    }
}
