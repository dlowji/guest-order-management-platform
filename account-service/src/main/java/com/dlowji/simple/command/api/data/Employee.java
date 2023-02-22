package com.dlowji.simple.command.api.data;

import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_employee")
public class Employee extends TimeStamp {
    @Id
    private String employeeId;
    private String fullName;
    private String email;
    private boolean gender;
    private BigDecimal salary;
    private LocalDate dob;
    private String phone;
    private String address;
    @OneToOne
    private Role role;
}
