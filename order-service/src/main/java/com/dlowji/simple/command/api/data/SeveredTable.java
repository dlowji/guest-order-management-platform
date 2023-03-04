package com.dlowji.simple.command.api.data;

import com.dlowji.simple.command.api.converter.TableStatusConverter;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.data.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_severed_table")
public class SeveredTable extends TimeStamp {
    @Id
    private String tableId;
    private String code;
    @Column(name = "TABLE_STATUS")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus tableStatus;
    private Integer capacity;
}
