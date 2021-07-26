package com.epam.orderapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class PlaceOrderCommand {

    @TargetAggregateIdentifier
    private String orderId;

    public PlaceOrderCommand(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
