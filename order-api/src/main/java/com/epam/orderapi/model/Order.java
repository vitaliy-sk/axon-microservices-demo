package com.epam.orderapi.model;

import com.epam.orderapi.command.AddItemCommand;
import com.epam.orderapi.command.NewOrderCommand;
import com.epam.orderapi.command.PlaceOrderCommand;
import com.epam.orderapi.event.OrderCreatedEvent;
import com.epam.orderapi.event.OrderPlacedEvent;
import com.epam.orderapi.query.GetOrderQuery;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.modelling.command.AggregateCreationPolicy;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.CreationPolicy;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.context.event.EventListener;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Aggregate
@Entity(name = "orders")
public class Order {

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
