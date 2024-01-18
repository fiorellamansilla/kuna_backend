package com.kuna_backend.controllers;

import com.kuna_backend.dtos.ResponseDto;
import com.kuna_backend.dtos.client.SignInDto;
import com.kuna_backend.dtos.client.SignInResponseDto;
import com.kuna_backend.dtos.client.SignupDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
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
import org.springframework.web.bind.annotation.DeleteMapping;

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
    public ResponseEntity<Client> get(@PathVariable long id) {
        try {
            Client client = clientService.getClient(id);
            return new ResponseEntity<Client>(client, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Client>(HttpStatus.NOT_FOUND);
        }
    }

     // CREATE Endpoint for Sign Up
    @PostMapping(path = "/signup")
    public ResponseDto signUp(@RequestBody SignupDto signupDto) {
        return clientService.signUp(signupDto);
    }

    // CREATE Endpoint for Sign In
    @PostMapping(path = "/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return clientService.signIn(signInDto);
    }

    //UPDATE a Client / Endpoint
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<Client> update(@RequestBody Client client, @PathVariable Integer id) {
//        try {
//            Client existClient = clientService.getClient(id);
//            client.setId(id);
//            clientService.createdClient(client);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    // GET all Orders for a Client / One-to-many relationship Endpoint
    @GetMapping(path = "/{id}/orders")
    public List<Order> retrieveOrdersForClient(@PathVariable long id) {

        Client client = clientService.getClient(id);

        if (client==null)
            throw new NoSuchElementException("id:"+id);

        return client.getOrders();
    }

    //DELETE one Client by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable long id) {
        clientService.deleteClient(id);
    }
}
