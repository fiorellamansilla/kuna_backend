package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.CartItemNotExistException;
import com.kuna_backend.exceptions.ItemNotExistsException;
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


    // POST/CREATE Add items to Shopping Cart endpoint
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) throws AuthenticationFailException, ItemNotExistsException {

        // Authenticate the token
        authenticationService.authenticate(token);
        // Find the client
        Client client = authenticationService.getClient(token);
        // Check if the item_id is valid or not
        Item item = itemService.getItemById(addToCartDto.getItemId());
        System.out.println("Item to add" + item.getName());
        // Add item to the client's cart
        cartService.addToCart(addToCartDto, item, client);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }


    // GET/RETRIEVE all items from Cart for a Client endpoint
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) {

        // Authenticate the token
        authenticationService.authenticate(token);
        // Find the client
        Client client = authenticationService.getClient(token);
        // Get cart items
        CartDto cartDto = cartService.listCartItems(client);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    // DELETE a cart Item for a Client endpoint
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Integer itemID,
                                                      @RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {

        authenticationService.authenticate(token);
        int clientId = authenticationService.getClient(token).getId();
        // Delete a cart Item
        cartService.deleteCartItem(itemID, clientId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "The Item has been removed"), HttpStatus.OK);
    }

}
