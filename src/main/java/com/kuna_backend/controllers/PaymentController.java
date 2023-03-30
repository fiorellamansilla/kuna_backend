package com.kuna_backend.controllers;

import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.dtos.checkout.StripeResponse;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Payment;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.PaymentService;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private AuthenticationService authenticationService;

    // Stripe Create Session Api
    @PostMapping("/create-checkout-session")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
        // Create the Stripe session
        Session session = paymentService.createSession(checkoutItemDtoList);
        StripeResponse stripeResponse = new StripeResponse(session.getId());
        // Send the Stripe session id in response
        return new ResponseEntity<StripeResponse>(stripeResponse, HttpStatus.OK);
    }

    // Retrieve payment details from Stripe session and save it into the database
    @GetMapping("/save-payment")
    public ResponseEntity<String> processPayment(@RequestParam("stripeToken") String stripeToken, @RequestParam("token") String token) {
        try {
            // Validate token
            authenticationService.authenticate(token);
            // Retrieve Client
            Client client = authenticationService.getClient(token);
            // Save payment to database
            paymentService.savePaymentFromSession(stripeToken, client);
            return new ResponseEntity<>("Payment created and saved successfully", HttpStatus.OK);
        } catch (StripeException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to save payment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET All Payments / Endpoint
    @GetMapping(path = "/all")
    public List<Payment> list(){
        return (List<Payment>) paymentService.getAllPayments();
    }

    // GET a Payment by ID / Endpoint
    @GetMapping(path = "/{id}")
    public ResponseEntity<Payment> get(@PathVariable Integer id) {
        try {
            Payment payment = paymentService.getPayment(id);
            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
        }
    }


    // DELETE a Payment by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        paymentService.deletePayment(id);}
}
