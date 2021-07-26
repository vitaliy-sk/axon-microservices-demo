package com.epam.orderapi.model;

import com.epam.core.command.UpdateOrderStatus;
import com.epam.core.event.OrderCreatedEvent;
import com.epam.core.event.OrderPlacedEvent;
import com.epam.orderapi.command.AddItemCommand;
import com.epam.orderapi.command.NewOrderCommand;
import com.epam.orderapi.command.PlaceOrderCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.spring.stereotype.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.Set;

@Aggregate
@Entity(name = "orders")
public class Order {

    private transient static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    @AggregateIdentifier
    private String id;

    private String status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    private final Set<String> items = new HashSet<>();

    @CommandHandler
    @CreationPolicy(AggregateCreationPolicy.ALWAYS)
    public String newOrder(NewOrderCommand command) throws Exception {
        this.id = command.getOrderId();
        AggregateLifecycle.apply(new OrderCreatedEvent(id));
        return id;
    }

    @EventHandler
    private void orderCreated(OrderCreatedEvent event) {
        this.status = "NEW";
    }

    @CommandHandler
    public void addItem(AddItemCommand addItemCommand) {
        String product = addItemCommand.getProduct();
        if (items.contains(product)) throw new IllegalArgumentException("Item already in the cart");
        items.add(product);
    }

    @CommandHandler
    public void placeOrder(PlaceOrderCommand placeOrderCommand){
        this.status = "PLACED";
        AggregateLifecycle.apply(new OrderPlacedEvent(id));
    }

    @CommandHandler
    public void updateStatus(UpdateOrderStatus updateOrderStatus) {

        if (items.contains("fail")) {
            LOG.info("Simulate error");
            throw new RuntimeException("Smth happened wrong");
        }

        LOG.info("Order {} status change {} -> {}", id, status, updateOrderStatus.getStatus());
        this.status = updateOrderStatus.getStatus();
    }

    public String getId() {
        return id;
    }
    public String getStatus() {
        return status;
    }
    public Set<String> getItems() {
        return items;
    }
}
