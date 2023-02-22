package com.kuna_backend.controllers;

import com.kuna_backend.models.Payment;
import com.kuna_backend.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

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

    //CREATE a Payment / Endpoint
    @PostMapping(path = "/")
    public void add (@RequestBody Payment payment) {
        paymentService.createPayment(payment);
    }

    // UPDATE a Payment / Endpoint
    @PutMapping(path = "/{id}")
    public ResponseEntity<Payment> update(@RequestBody Payment payment, @PathVariable Integer id) {
        try {
            Payment existPayment = paymentService.getPayment(id);
            payment.setId(id);
            paymentService.createPayment(payment);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // DELETE a Payment by ID / Endpoint
    @DeleteMapping(path = "/{id}")
    public void delete (@PathVariable Integer id) {
        paymentService.deletePayment(id);}
}
