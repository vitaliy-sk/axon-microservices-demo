package com.epam.orderapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;

public class NewOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    public NewOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
