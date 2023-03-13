package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Item;
import com.kuna_backend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CartService  {

    @Autowired
    CartRepository cartRepository;

    public void addToCart (AddToCartDto addToCartDto, Item item, Client client) {
        Cart cart = new Cart();
        cart.setItem(item);
        cart.setClient(client);
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setCreatedAt(new Date());

        // Save the cart
        cartRepository.save(cart);
    }
}
