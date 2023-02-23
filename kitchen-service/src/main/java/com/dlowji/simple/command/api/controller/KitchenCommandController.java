package com.dlowji.simple.command.api.controller;

import com.dlowji.simple.KitchenServiceApplication;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kitchens")
public class KitchenCommandController {
    private static final Logger LOGGER = LoggerFactory.getLogger(KitchenServiceApplication.class);

    private final CommandGateway commandGateway;

    public KitchenCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/dish/create")
    public String createDish() {
        return "";
    }
}
