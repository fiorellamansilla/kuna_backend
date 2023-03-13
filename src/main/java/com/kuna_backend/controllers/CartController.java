package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Item;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.CartService;
import com.kuna_backend.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ItemService itemService;

    // POST/CREATE Add to Shopping Cart endpoint
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) {

        // Authenticate the token
        authenticationService.authenticate(token);

        // Find the client
        Client client = authenticationService.getClient(token);

        cartService.addToCart(addToCartDto, client);

        return new ResponseEntity<>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);

    }



    // GET all cart items for a user endpoint

    // DELETE a cart item for a user endpoint
}
