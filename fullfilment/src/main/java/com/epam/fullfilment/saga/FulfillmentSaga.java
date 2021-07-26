package com.epam.fullfilment.saga;

import com.epam.core.command.UpdateOrderStatus;
import com.epam.core.event.OrderPlacedEvent;
import com.epam.fullfilment.command.PrepareShippingCommand;
import com.epam.fullfilment.event.ShippingArrivedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.invoke.MethodHandles;

@Saga
public class FulfillmentSaga {

    private transient static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Resource
    protected transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderPlacedEvent event) {

        LOG.info("Saga started");

        // client generated identifiers
        String shipmentId = String.format("ship_%s", event.getOrderId());

        // associate the Saga with these values, before sending the commands
        SagaLifecycle.associateWith("shipmentId", shipmentId);

        // send the commands
        commandGateway.send(new PrepareShippingCommand(event.getOrderId(), shipmentId));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ShippingArrivedEvent event) {
        commandGateway.send(new UpdateOrderStatus(event.getOrderId(), "SHIPPED"));
        SagaLifecycle.end();
    }

}
