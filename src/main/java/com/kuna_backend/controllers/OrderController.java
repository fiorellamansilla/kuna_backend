package com.kuna_backend.controllers;

import com.kuna_backend.dao.OrderDaoService;
import com.kuna_backend.entities.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class OrderController {

    private OrderDaoService service;

    public OrderController(OrderDaoService service) {
        this.service = service;
    }

    // GET All Orders Endpoint
    @GetMapping(path = "/order")
    public List<Order> retrieveAllOrders(){
        return service.findAll();
    }

    // GET an Order by ID Endpoint
    @GetMapping(path = "/order/{id}")
    public Order retrieveOrder (@PathVariable int id) {
        Order order =  service. findOne(id);

        if (order==null)
            throw new ElementNotFoundException("id:" +id);

        return order;
    }

    // POST an Order Endpoint
    @PostMapping(path = "/order")
    public ResponseEntity<Order> createOrder (@RequestBody Order order) {
        Order savedOrder = service.save(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedOrder.getOrder_id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE one Order by ID Endpoint
    @DeleteMapping(path = "/order/{id}")
    public void deleteOrder (@PathVariable int id) {
        service.deleteById(id);
    }

}
