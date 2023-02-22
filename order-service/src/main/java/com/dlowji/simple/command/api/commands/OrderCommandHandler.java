package com.dlowji.simple.command.api.commands;

import com.dlowji.simple.command.api.data.IOrderRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandHandler {
    @CommandHandler
    public void handle(RequestOrderChangeCommand requestOrderChangeCommand, CommandGateway commandGateway, IOrderRepository orderRepository) {


    }
}

