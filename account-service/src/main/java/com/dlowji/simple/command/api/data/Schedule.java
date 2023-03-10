package com.dlowji.simple.command.api.data;

import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_schedule")
public class Schedule extends TimeStamp {
    @Id
    private String scheduleId;
    private LocalTime startWorkHour;
    private LocalTime endWorkHour;
    private LocalDate workDate;
    @OneToOne
    private Employee employee;
}
