package com.kuna_backend.services;

import com.kuna_backend.models.OrderItem;
import com.kuna_backend.repositories.OrderItemsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderItemsService {

    @Autowired
    private OrderItemsRepository orderItemsRepository;

    public void addOrderedItems (OrderItem orderItem) {
        orderItemsRepository.save(orderItem);
    }
}
