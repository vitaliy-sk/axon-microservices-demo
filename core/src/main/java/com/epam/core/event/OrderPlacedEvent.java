package com.epam.core.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class OrderPlacedEvent {

    @TargetAggregateIdentifier
    private String orderId;

    public OrderPlacedEvent(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }
}
