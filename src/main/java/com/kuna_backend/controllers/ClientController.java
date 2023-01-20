package com.kuna_backend.controllers;

import com.kuna_backend.entities.Client;
import com.kuna_backend.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // GET  all Clients / Endpoint
    @GetMapping(path = "/all")
    public Iterable<Client> list(){
        return clientService.getAllClients();
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

}
