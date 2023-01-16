package com.kuna_backend.controllers;

import com.kuna_backend.entities.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ItemController {

    private ItemDaoService service;

    public ItemController(ItemDaoService service) {
        this.service = service;
    }

    // GET All Items Endpoint
    @GetMapping(path = "/item")
    public List<Item> retrieveAllItems(){
        return service.findAll();
    }

    // GET an Item by ID / Endpoint
    @GetMapping(path = "/item/{id}")
    public Item retrieveItem (@PathVariable int id) {
        Item item =  service. findOne(id);

        if (item==null)
            throw new ElementNotFoundException("id:" +id);

        return item;
    }

    // POST an Item Endpoint
    @PostMapping(path = "/item")
    public ResponseEntity<Item> createItem (@RequestBody Item item) {
        Item savedItem = service.save(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedItem.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE one Item by ID / Endpoint
    @DeleteMapping(path = "/item/{id}")
    public void deleteItem (@PathVariable int id) {
        service.deleteById(id);
    }


    // UPDATE one Item by ID / Endpoint
    @PutMapping (path = "/item/{id}")
    public ResponseEntity<Item> updateItem (@RequestBody Item item, @PathVariable int id) {
        Item updatedItem = service.findOne(id);

        if (item==null)
            throw new ElementNotFoundException("id:" +id);

        service.save(updatedItem);

        return ResponseEntity.ok(updatedItem);
    }

}
