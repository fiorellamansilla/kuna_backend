package com.kuna_backend.repositories;

import com.kuna_backend.entities.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository <Item, Integer> {
}
