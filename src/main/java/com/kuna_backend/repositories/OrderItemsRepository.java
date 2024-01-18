package com.kuna_backend.repositories;

import com.kuna_backend.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {
}
