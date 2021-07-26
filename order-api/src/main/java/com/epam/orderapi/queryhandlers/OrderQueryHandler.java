package com.epam.orderapi.queryhandlers;

import com.epam.orderapi.model.Order;
import com.epam.orderapi.query.GetOrderQuery;
import com.epam.orderapi.repository.OrdersRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderQueryHandler {

    @Resource
    private OrdersRepository ordersRepository;

    @QueryHandler
    public Order getOrder(GetOrderQuery query) {
        return ordersRepository.findOrderById(query.getOrderId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
    }

}
