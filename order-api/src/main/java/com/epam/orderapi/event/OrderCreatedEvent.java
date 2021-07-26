package com.epam.orderapi.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class OrderCreatedEvent {

    @TargetAggregateIdentifier
    private String id;

    public OrderCreatedEvent(String id) {
        this.id = id;
    }
}
