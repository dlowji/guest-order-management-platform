package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterOrderRequest {
    @NotNull(message = "Please enter timestamp")
    private long timestamp;

    @NotNull(message = "Please enter filter")
    @NotEmpty(message = "Please enter valid filter")
    private String filter;
}
