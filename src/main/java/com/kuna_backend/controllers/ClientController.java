package com.kuna_backend.controllers;

import com.kuna_backend.entities.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ClientController {

    private ClientDaoService service;

    public ClientController(ClientDaoService service) {
        this.service = service;
    }

    // GET  all Clients Endpoint
    @GetMapping(path = "/client")
    public List<Client> retrieveAllClients(){
        return service.findAll();
    }

    // GET a Client by ID / Endpoint
    @GetMapping(path = "/client/{id}")
    public Client retrieveClient (@PathVariable int id) {
        Client client =  service. findOne(id);

        if (client==null)
            throw new ElementNotFoundException("id:" +id);

        return client;
    }

    // POST a Client Endpoint
    @PostMapping(path = "/client")
    public ResponseEntity<Client> createClient (@RequestBody Client client) {
        Client savedClient = service.save(client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedClient.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE one Client by ID / Endpoint

    @DeleteMapping(path = "/client/{id}")
    public void deleteClient (@PathVariable int id) {
        service.deleteById(id);
    }


    // UPDATE one Client by ID / Endpoint
    @PutMapping (path = "/client/{id}")
    public ResponseEntity<Client> updateClient (@RequestBody Client client, @PathVariable int id) {
        Client updatedClient = service.findOne(id);

        if (client==null)
            throw new ElementNotFoundException("id:" +id);

        service.save(updatedClient);

        return ResponseEntity.ok(updatedClient);
    }

}
