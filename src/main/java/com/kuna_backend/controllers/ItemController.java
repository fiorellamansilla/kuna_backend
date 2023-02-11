package com.kuna_backend.controllers;

import com.kuna_backend.entities.Item;
import com.kuna_backend.services.ItemService;

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
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // GET All Items / Endpoint
    @GetMapping(path = "/all")
    public List<Item> list(){
        return (List<Item>) itemService.getAllItems();
    }

    // GET an Item by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Item> get(@PathVariable Integer id) {
        try {
            Item item = itemService.getItem(id);
            return new ResponseEntity<Item>(item, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE an Item / Endpoint
    @PostMapping(path = "/")
    public void add (@RequestBody Item item) {
        itemService.createItem(item);
    }

    // UPDATE an Item / Endpoint
    @PutMapping(path = "/{id}")
    public ResponseEntity<Item> update(@RequestBody Item item, @PathVariable Integer id) {
        try {
            Item existItem = itemService.getItem(id);
            item.setId(id);
            itemService.createItem(item);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE one Item by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        itemService.deleteItem(id);
    }

}
