package com.epam.orderapi.repository;

import com.epam.orderapi.model.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrdersRepository extends CrudRepository<Order, String> {
    Optional<Order> findOrderById(String id);
}
