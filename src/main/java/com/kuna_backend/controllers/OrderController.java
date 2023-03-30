package com.kuna_backend.controllers;

import com.kuna_backend.common.ApiResponse;
import com.kuna_backend.dtos.shipping.ShippingDetailDto;
import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.exceptions.OrderNotFoundException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.models.Payment;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.OrderService;
import com.kuna_backend.services.PaymentService;
import com.kuna_backend.services.ShippingDetailService;

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

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ShippingDetailService shippingDetailService;


    // POST Endpoint - Place order after Checkout session
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> placeOrder (
            @RequestParam("token") String token,
            @RequestParam("stripeToken") String stripeToken,
            @RequestBody ShippingDetailDto shippingDetailDto)
            throws AuthenticationFailException {
        // Validate token
        authenticationService.authenticate(token);
        // Retrieve Client
        Client client = authenticationService.getClient(token);
        // Retrieve Payment
        Payment payment = paymentService.getPayment(stripeToken);
        // Save the shipping details
        ShippingDetail shippingDetail = shippingDetailService.addShippingDetail(shippingDetailDto, client);
        // Place the order
        orderService.placeOrder(client, payment, shippingDetail);
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

    // GET Order Items for an Order / Endpoint
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

    //DELETE one Order by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        orderService.deleteOrder(id);
    }

}


