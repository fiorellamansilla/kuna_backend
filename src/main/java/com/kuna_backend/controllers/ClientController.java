package com.kuna_backend.controllers;

import com.kuna_backend.entities.Client;
import com.kuna_backend.entities.Order;
import com.kuna_backend.services.ClientService;

import com.kuna_backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private OrderService orderService;

    // GET  all Clients / Endpoint
    @GetMapping(path = "/all")
    public List<Client> list(){
        return (List<Client>) clientService.getAllClients();
    }

    // GET a Client by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Client> get(@PathVariable Integer id) {
        try {
            Client client = clientService.getClient(id);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE a Client / Endpoint
    @PostMapping(path = "/")
    public void add (@RequestBody Client client) {
        clientService.createClient(client);
    }

    // UPDATE a Client / Endpoint
    @PutMapping(path = "/{id}")
    public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Integer id) {
        try {
            Client existClient = clientService.getClient(id);
            client.setId(id);
            clientService.createClient(client);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE one Client by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        clientService.deleteClient(id);
    }


    // Retrieve all Orders for a Client / One-to-many relationship Endpoint
    @GetMapping(path = "/{id}/orders")
    public List<Order> retrieveOrdersForClient(@PathVariable Integer id) {

        Client client = clientService.getClient(id);

        if (client==null)
            throw new NoSuchElementException("id:"+id);

        return client.getOrders();
    }

    // Create an Order for a specific Client / One-to-many relationship Endpoint
    @PostMapping(path = "/{id}/orders")
    public ResponseEntity<Order> createOrderForClient(@PathVariable Integer id, @RequestBody Order order) {

        Client client = clientService.getClient(id);

        if (client==null)
            throw new NoSuchElementException("id:"+id);

        order.setClient(client);

        orderService.createOrder(order);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
