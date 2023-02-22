package com.dlowji.simple.command.api.data;

import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.ZonedDateTime;
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_account")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends TimeStamp {
    @Id
    private String accountId;
    private String username;
    private String password;
    private ZonedDateTime lastLogin;
    @OneToOne
    private Employee employee;
}
