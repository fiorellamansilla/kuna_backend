package com.kuna_backend.controllers;

import com.kuna_backend.dao.UserDaoService;
import com.kuna_backend.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class UserController {

    private UserDaoService service;

    public UserController(UserDaoService service) {this.service = service;}

    // GET All Users Endpoint
    @GetMapping(path = "/user")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    // GET a User by ID / Endpoint
    @GetMapping(path = "/user/{id}")
    public User retrieveUser (@PathVariable int id) {
        User user =  service. findOne(id);

        if (user==null)
            throw new ElementNotFoundException("id:" +id);

        return user;
    }

    // POST a User Endpoint
    @PostMapping(path = "/user")
    public ResponseEntity<User> createUser (@RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE one User by ID /  Endpoint
    @DeleteMapping(path = "/user/{id}")
    public void deleteUser (@PathVariable int id) {
        service.deleteById(id);
    }


    // UPDATE one User by ID /  Endpoint
    @PutMapping (path = "/user/{id}")
    public ResponseEntity<User> updateUser (@RequestBody User user, @PathVariable int id) {
        User updatedUser = service.findOne(id);

        if (user==null)
            throw new ElementNotFoundException("id:" +id);

        service.save(updatedUser);

        return ResponseEntity.ok(updatedUser);
    }

}
