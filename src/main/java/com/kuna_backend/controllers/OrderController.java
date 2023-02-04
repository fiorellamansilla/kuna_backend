package com.kuna_backend.controllers;

import com.kuna_backend.entities.Order;
import com.kuna_backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // GET All Orders / Endpoint
    @GetMapping(path = "/all")
    public List<Order> list(){
        return (List<Order>) orderService.getAllOrders();
    }

    // GET an Order by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> get(@PathVariable Integer id) {
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE an Order / Endpoint
    @PostMapping(path = "/")
    public void add (@RequestBody Order order) {
        orderService.createOrder(order);
    }

    // UPDATE an Order / Endpoint
    @PutMapping(path = "/{id}")
    public ResponseEntity<Order> update(@RequestBody Order order, @PathVariable Integer id) {
        try {
            Order existOrder = orderService.getOrder(id);
            order.setId(id);
            orderService.createOrder(order);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE one Order by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

}
