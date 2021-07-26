package com.epam.core.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateOrderStatus {

    @TargetAggregateIdentifier
    private String id;
    private String status;

    public UpdateOrderStatus(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
