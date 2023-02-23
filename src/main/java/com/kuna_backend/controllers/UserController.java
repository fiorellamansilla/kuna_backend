package com.kuna_backend.controllers;

import com.kuna_backend.models.User;
import com.kuna_backend.services.UserService;
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

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // GET All Users / Endpoint
    @GetMapping(path = "/all")
    public List<User> list() {
        return (List<User>) userService.getAllUsers();
    }

    // GET a User by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.getUser(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE a User / Endpoint
    @PostMapping(path = "/")
    public void add(@RequestBody User user) {
        userService.createUser(user);
    }

    // UPDATE a User / Endpoint
    @PutMapping(path = "/{id}")
    public ResponseEntity<User> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existUser = userService.getUser(id);
            user.setId(id);
            userService.createUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE one User by ID /  Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

}


