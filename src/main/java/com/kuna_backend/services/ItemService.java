package com.kuna_backend.services;

import com.kuna_backend.entities.Item;
import com.kuna_backend.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public Iterable<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem (Integer id) {
        return itemRepository.findById(id).get();
    }

    public void createItem (Item item) {
        itemRepository.save(item);
    }

    public void deleteItem (Integer id) {
        itemRepository.deleteById(id);
    }

}
