package com.epam.orderapi.query;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class GetOrderQuery {

    @TargetAggregateIdentifier
    private String orderId;

    public GetOrderQuery(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

}
