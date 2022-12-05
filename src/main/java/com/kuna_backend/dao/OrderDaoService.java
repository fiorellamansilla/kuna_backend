package com.kuna_backend.dao;

import com.kuna_backend.entities.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class OrderDaoService {

    private static List<Order> orders = new ArrayList<>();

    private static int ordersCount = 0;

    static {
        orders.add(new Order(++ordersCount,
                10.0F,
                "Fiorella",
                "Bei der Flottbeker Muhle",
                "Hamburg",
                "22607",
                "Germany",
                "0842039131",
                "fmansilla@gmail.com",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "123"));
    };

    // GET all orders Method
    public List<Order> findAll() {
        return orders;
    }


    // Get one Order by ID Method
    public Order findOne(int id) {
        Predicate<? super Order> predicate = order -> order.getOrder_id().equals(id);
        return orders.stream().filter(predicate).findFirst().orElse(null);
    }

    // POST a client Method
    public Order save(Order order) {
        order.setOrder_id(++ordersCount);
        orders.add(order);
        return order;
    }

    // Delete Order by Id Method
    public void deleteById(int id) {
        Predicate<? super Order> predicate = order -> order.getOrder_id().equals(id);
        orders.removeIf(predicate);
    }

}
