package com.epam.fullfilment.model;

import com.epam.fullfilment.command.PrepareShippingCommand;
import com.epam.fullfilment.event.ShippingArrivedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.invoke.MethodHandles;

@Aggregate
@Entity
public class Shipment {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    private String id;

    @Column
    private String orderId;

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    public void handle(PrepareShippingCommand command) {
        LOG.info("Sending shipment {} doing some logic....", command.getShipmentId());

        this.id = command.getShipmentId();
        this.orderId = command.getOrderId();

        AggregateLifecycle.apply(new ShippingArrivedEvent(command.getOrderId(), command.getShipmentId()));
    }


}
