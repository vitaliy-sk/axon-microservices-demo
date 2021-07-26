package com.epam.orderapi.api;

import com.epam.orderapi.command.AddItemCommand;
import com.epam.orderapi.command.NewOrderCommand;
import com.epam.orderapi.command.PlaceOrderCommand;
import com.epam.orderapi.model.Order;
import com.epam.orderapi.query.GetOrderQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/order")
public class OrderApi {

    @Resource
    protected CommandGateway commandGateway;

    @Resource
    protected QueryGateway queryGateway;

    @PostMapping
    public Object createNewOrder() {
        String id = commandGateway.sendAndWait(new NewOrderCommand(UUID.randomUUID().toString()));
        return Map.of("id", id);
    }

    @PostMapping("/{orderId}")
    public void addToOrder(@PathVariable String orderId, @RequestBody Map<String, String> item) {
        String productCode = item.get("item");
        commandGateway.sendAndWait(new AddItemCommand(orderId, productCode));
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        return queryGateway.query(new GetOrderQuery(orderId), Order.class).join();
    }

    @PostMapping("/{orderId}/place")
    public void addToOrder(@PathVariable String orderId) {
        commandGateway.sendAndWait(new PlaceOrderCommand(orderId));
    }


}
