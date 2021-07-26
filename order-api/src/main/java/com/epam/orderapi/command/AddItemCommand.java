package com.epam.orderapi.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class AddItemCommand {

    @TargetAggregateIdentifier
    private String orderId;

    private String product;

    public AddItemCommand(String orderId, String product) {
        this.orderId = orderId;
        this.product = product;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

}
