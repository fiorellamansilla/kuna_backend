package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Item;
import com.kuna_backend.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public CartDto listCartItems(Client client) {
        List<Cart> cartList = cartRepository.findAllByClientOrderByCreatedDateDesc(client);

        List<CartItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart:cartList) {
            CartItemDto cartItemDto = new CartItemDto(cart);
            totalCost += cartItemDto.getQuantity() * cart.getItem().getPrice();
            cartItems.add(cartItemDto);
        }

        CartDto cartDto = new CartDto();
        cartDto.setTotalCost(totalCost);
        cartDto.setCartItems(cartItems);
        return cartDto;
    }
}
