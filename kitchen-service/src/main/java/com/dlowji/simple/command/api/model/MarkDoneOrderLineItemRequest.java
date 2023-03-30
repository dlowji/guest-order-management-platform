package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MarkDoneOrderLineItemRequest {
    @NotNull(message = "Please enter order line item id")
    private Long id;
}
