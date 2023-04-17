package com.kuna_backend.repositories;

import com.kuna_backend.enums.OrderStatus;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Method to fetch an ordered list of Orders from a Client by date of each entry in the order table.
    List<Order> findAllByClientOrderByCreatedAtDesc (Client client);

    List<Order> findAllByOrderStatus (OrderStatus orderStatus);
}
