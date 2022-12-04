package com.kuna_backend.controllers;

import com.kuna_backend.dao.ClientDaoService;
import com.kuna_backend.dao.ItemDaoService;
import com.kuna_backend.entities.Client;
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

    // GET  /All Items
    @GetMapping(path = "/item")
    public List<Item> retrieveAllItems(){
        return service.findAll();
    }

    // GET an Item by ID
    @GetMapping(path = "/item/{id}")
    public Item retrieveItem (@PathVariable int id) {
        Item item =  service. findOne(id);

        if (item==null)
            throw new ElementNotFoundException("id:" +id);

        return item;
    }

    // POST /item
    @PostMapping(path = "/item")
    public ResponseEntity<Item> createItem (@RequestBody Item item) {
        Item savedItem = service.save(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedItem.getItem_id())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    //DELETE one Item by ID

    @DeleteMapping(path = "/item/{id}")
    public void deleteItem (@PathVariable int id) {
        service.deleteById(id);
    }

}
