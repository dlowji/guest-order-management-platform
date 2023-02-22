package com.dlowji.simple.command.api.data;

import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_role")
public class Role extends TimeStamp {
    @Id
    private String roleId;
    private String roleName;
    private String description;
}
