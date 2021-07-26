package com.epam.fullfilment.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class PrepareShippingCommand {

    private String orderId;

    @TargetAggregateIdentifier
    private String shipmentId;

    public PrepareShippingCommand(String orderId, String shipmentId) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public String getOrderId() {
        return orderId;
    }
}
