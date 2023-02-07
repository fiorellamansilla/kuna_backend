package com.kuna_backend.controllers;

import com.kuna_backend.entities.Item;
import com.kuna_backend.entities.Order;
import com.kuna_backend.services.ItemService;
import com.kuna_backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

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


    // Retrieve all Items for an Order / Many-to-many relationship Endpoint
    @GetMapping(path = "/{id}/items")
    public Set<Item> retrieveItemsForOrder(@PathVariable Integer id) {

        Order order = orderService.getOrder(id);

        if (order==null)
            throw new NoSuchElementException("id:"+id);

        return order.getItems();
    }


    // Create an Item for a specific Order / Many-to-many relationship Endpoint
    @PostMapping(path = "/{id}/items")
    public ResponseEntity<Item> createItemForOrder(@PathVariable Integer id, @RequestBody Item item) {

        Order order = orderService.getOrder(id);

        if (order==null)
            throw new NoSuchElementException("id:"+id);

        item.setOrders((Set<Order>) order);

        itemService.createItem(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}


