package com.dlowji.simple.command.api.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.springframework.stereotype.Component;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateTableCommand {
    @TargetAggregateIdentifier
    private String tableId;
    private String code;
    private String tableStatus;
    private Integer capacity;
}
