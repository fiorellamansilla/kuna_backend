package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.exceptions.CartItemNotExistException;
import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Item;
import com.kuna_backend.repositories.CartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartService  {

    @Autowired
    private CartRepository cartRepository;

    public CartService() {
    }

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart (AddToCartDto addToCartDto, Item item, Client client) {
        Cart cart = new Cart (item, addToCartDto.getQuantity(), client);
        // Save the cart
        cartRepository.save(cart);
    }

    public CartDto listCartItems(Client client) {
        List<Cart> cartList = cartRepository.findAllByClientOrderByCreatedDateDesc(client);
        List<CartItemDto> cartItems = new ArrayList<>();

        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }

        double totalCost = 0;
        for (CartItemDto cartItemDto : cartItems) {
            totalCost += (cartItemDto.getItem().getPrice() * cartItemDto.getQuantity());
        }
        return new CartDto(cartItems, totalCost);
    }

    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }

    public void deleteCartItem(int id, int clientId) throws CartItemNotExistException {
        if (!cartRepository.existsById(id))
            throw new CartItemNotExistException("Cart item id is invalid: " + id);
        cartRepository.deleteById(id);
    }

    public void deleteCartItems (int clientId) {
        cartRepository.deleteAll();
    }

    public void deleteClientCartItems (Client client) {
        cartRepository.deleteByClient(client);
    }
}
