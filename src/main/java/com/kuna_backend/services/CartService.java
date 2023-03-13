package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.models.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService  {

    @Autowired
    ItemService itemService;
    public void addToCart (AddToCartDto addToCartDto, Client client) {

        // Check if the item_id is valid or not
        itemService.findById(addToCartDto.getItemId());


        // Save the cart
    }
}
