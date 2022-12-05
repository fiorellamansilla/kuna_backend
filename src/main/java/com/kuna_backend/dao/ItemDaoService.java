package com.kuna_backend.dao;

import com.kuna_backend.entities.Item;
import com.kuna_backend.entities.enums.Color;
import com.kuna_backend.entities.enums.Size;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ItemDaoService {

    private static List<Item> items = new ArrayList<>();

    private static int itemsCount = 0;

    static {
        items.add(new Item(++itemsCount,
                "Mitones",
                "Mitones de algodón",
                Size.NEWBORN,
                Color.WHITE,
                10.50F,
                0.0F,
                "123",
                1,
                "/mitones.jpg",
                LocalDateTime.now(),
                LocalDateTime.now(),
                LocalDateTime.now()));
    };

    // Get all items Method
    public List<Item> findAll() {
        return items;
    }


    // Get one Item by ID Method
    public Item findOne(int id) {
        Predicate<? super Item> predicate = item -> item.getItem_id().equals(id);
        return items.stream().filter(predicate).findFirst().orElse(null);
    }

    // POST a client Method
    public Item save(Item item) {
        item.setItem_id(++itemsCount);
        items.add(item);
        return item;
    }

    // Delete Item by Id Method
    public void deleteById(int id) {
        Predicate<? super Item> predicate = item -> item.getItem_id().equals(id);
        items.removeIf(predicate);
    }

}
