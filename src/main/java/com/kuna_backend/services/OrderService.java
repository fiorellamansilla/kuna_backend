package com.kuna_backend.services;

import com.kuna_backend.entities.Order;
import com.kuna_backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order getOrder (Integer id) {
        return orderRepository.findById(id).get();
    }

    public void createOrder (Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder (Integer id) {
        orderRepository.deleteById(id);
    }

}
