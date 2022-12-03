package com.kuna_backend.controllers;

import com.kuna_backend.dao.ClientDaoService;
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

    // GET Method / All Clients
    @GetMapping(path = "/client")
    public List<Client> retrieveAllClients(){
        return service.findAll();
    }

    // GET Method / Client by ID
    @GetMapping(path = "/client/{id}")
    public Client retrieveClient (@PathVariable int id) {
        Client client =  service. findOne(id);

        if (client==null)
            throw new ClientNotFoundException("id:" +id);

        return client;
    }

    // POST Method /clients
    @PostMapping(path = "/client")
    public ResponseEntity<Client> createClient (@RequestBody Client client) {
        Client savedClient = service.save(client);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedClient.getClient_id())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
