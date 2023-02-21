package com.dlowji.simple.command.api.commands;

import com.dlowji.simple.command.api.data.IOrderRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@Component
public class OrderCommandHandler {
    @CommandHandler
    public void handle(RequestOrderChangeCommand requestOrderChangeCommand, CommandGateway commandGateway, IOrderRepository orderRepository) {
        if (!orderRepository.existsById(requestOrderChangeCommand.getOrderId())) {
            throw new IllegalStateException(String.format("Order with id %s not exists", requestOrderChangeCommand.getOrderId()));
        }

        boolean select = requestOrderChangeCommand.isSelect();
        if (select) {
            SelectDishCommand selectDishCommand = SelectDishCommand.builder()
                    .orderId(requestOrderChangeCommand.getOrderId())
                    .dishId(requestOrderChangeCommand.getDishId())
                    .unit(requestOrderChangeCommand.getUnit())
                    .price(requestOrderChangeCommand.getPrice())
                    .quantity(requestOrderChangeCommand.getQuantity())
                    .build();
            commandGateway.send(selectDishCommand);
        } else {
            DeSelectDishCommand deSelectDishCommand = DeSelectDishCommand.builder()
                    .orderId(requestOrderChangeCommand.getOrderId())
                    .dishId(requestOrderChangeCommand.getDishId())
                    .unit(requestOrderChangeCommand.getUnit())
                    .price(requestOrderChangeCommand.getPrice())
                    .quantity(requestOrderChangeCommand.getQuantity())
                    .build();
            commandGateway.send(deSelectDishCommand);
        }

    }
}

