package com.kuna_backend.controllers;

import com.kuna_backend.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // POST Endpoint

    // GET all cart items for a user endpoint

    // DELETE a cart item for a user endpoint
}
