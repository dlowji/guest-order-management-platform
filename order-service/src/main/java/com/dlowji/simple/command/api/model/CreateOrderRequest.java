package com.dlowji.simple.command.api.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {
   @NotNull(message = "Please enter account id")
   @NotEmpty(message = "Please enter a valid account id")
   private String accountId;
   @NotNull(message = "Please enter table id")
   @NotEmpty(message = "Please enter a valid table id")
   private String tableId;
}
