package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.dtos.checkout.StripeResponse;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.OrderNotFoundException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.ItemService;
import com.kuna_backend.services.OrderService;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ItemService itemService;

    // Stripe Create Session Api
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // Create the Stripe session
        Session session = orderService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // Send the Stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // POST Endpoint - Place order after Checkout session
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder (@RequestParam("token") String token, @RequestParam("sessionId") String sessionId) throws AuthenticationFailException {
        // Validate token
        authenticationService.authenticate(token);
        // Retrieve Client
        Client client = authenticationService.getClient(token);
        // Place the order
        orderService.placeOrder(client, sessionId);
        return new ResponseEntity<>(new ApiResponse(true, "The Order has been placed"), HttpStatus.CREATED);
    }

    // GET All Orders for a Client / Endpoint
    @GetMapping(path = "/all")
    public ResponseEntity<List<Order>> getAllOrders(@RequestParam("token") String token) throws AuthenticationFailException {
        // Validate the token
        authenticationService.authenticate(token);
        // Retrieve the client
        Client client = authenticationService.getClient(token);
        // Get Orders
        List<Order> orderDtoList = orderService.listOrders(client);

        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    // GET Order Items for an Order
    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable ("id") Integer id, @RequestParam ("token") String token)
            throws AuthenticationFailException {

        // Validate token
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<>(order, HttpStatus.OK);
        }
        catch (OrderNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    // UPDATE an Order / Endpoint
//    @PutMapping(path = "/{id}")
//    public ResponseEntity<Order> update(@RequestBody Order order, @PathVariable Integer id) {
//        try {
//            Order existOrder = orderService.getOrder(id);
//            order.setId(id);
//            orderService.createOrder(order);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    //DELETE one Order by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }


//    // Retrieve all Items for an Order / Many-to-many relationship Endpoint
//    @GetMapping(path = "/{id}/items")
//    public Set<Item> retrieveItemsForOrder(@PathVariable Integer id) {
//
//        Order order = orderService.getOrder(id);
//
//        if (order==null)
//            throw new NoSuchElementException("id:"+id);
//
//        return order.getItems();
//    }

}


