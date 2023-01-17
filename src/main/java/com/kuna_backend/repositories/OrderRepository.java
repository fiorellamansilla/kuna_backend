package com.kuna_backend.repositories;

import com.kuna_backend.entities.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
