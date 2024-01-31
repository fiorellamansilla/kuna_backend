package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.CartItemNotExistException;
import com.kuna_backend.exceptions.ProductNotExistsException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.CartService;
import com.kuna_backend.services.ProductService;
import com.kuna_backend.services.ProductVariationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping ("/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductVariationService productVariationService;
    @Autowired
    private  ProductService productService;
    @Autowired
    private AuthenticationService authenticationService;


    // POST/CREATE Add items to Shopping Cart
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addToCart(@RequestBody AddToCartDto addToCartDto,
                                                 @RequestParam("token") String token) throws AuthenticationFailException, ProductNotExistsException {

        // Authenticate the token
        authenticationService.authenticate(token);
        // Find the client
        Client client = authenticationService.getClient(token);
        // Check if the item_id is valid or not
        ProductVariation productVariation = productVariationService.getProductVariationById(addToCartDto.getProductVariationId());
        System.out.println("Product to add" + productVariation.getProduct());
        // Add product to the client's cart
        cartService.addToCart(addToCartDto, productVariation, client);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Added to cart"), HttpStatus.CREATED);
    }


    // GET/RETRIEVE all items from Cart for a Client
    @GetMapping("/")
    public ResponseEntity<CartDto> getCartItems(@RequestParam("token") String token) throws AuthenticationFailException {

        authenticationService.authenticate(token);
        Client client = authenticationService.getClient(token);
        // Get cart items
        CartDto cartDto = cartService.listCartItems(client);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.OK);
    }

    // DELETE a cart Item for a Client endpoint
    @DeleteMapping("/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long itemID,
                                                      @RequestParam("token") String token) throws AuthenticationFailException, CartItemNotExistException {

        authenticationService.authenticate(token);
        Long clientId = authenticationService.getClient(token).getId();
        // Delete a cart Item
        cartService.deleteCartItem(itemID, clientId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "The Item has been removed"), HttpStatus.OK);
    }
}
