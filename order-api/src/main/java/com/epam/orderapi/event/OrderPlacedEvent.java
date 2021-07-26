package com.epam.orderapi.event;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class OrderPlacedEvent {

    @TargetAggregateIdentifier
    private String id;

    public OrderPlacedEvent(String id) {
        this.id = id;
    }
}
