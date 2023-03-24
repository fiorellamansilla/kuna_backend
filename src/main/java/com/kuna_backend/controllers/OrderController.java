package com.kuna_backend.controllers;

import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.dtos.checkout.StripeResponse;
import com.kuna_backend.models.Item;
import com.kuna_backend.models.Order;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.ItemService;
import com.kuna_backend.services.OrderService;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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

    // GET All Orders / Endpoint
    @GetMapping(path = "/all")
    public List<Order> list(){
        return (List<Order>) orderService.getAllOrders();
    }

    // GET an Order by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> get(@PathVariable Integer id) {
        try {
            Order order = orderService.getOrder(id);
            return new ResponseEntity<Order>(order, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
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


    // Create an Item for a specific Order / Many-to-many relationship Endpoint
    @PostMapping(path = "/{id}/items")
    public ResponseEntity<Item> createItemForOrder(@PathVariable Integer id, @RequestBody Item item) {

        Order order = orderService.getOrder(id);

        if (order==null)
            throw new NoSuchElementException("id:"+id);

        item.setOrders((Set<Order>) order);

        itemService.createItem(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(item.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}


