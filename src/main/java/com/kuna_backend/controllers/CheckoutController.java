package com.kuna_backend.controllers;
import com.kuna_backend.entities.enums.Currency;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    //  http://localhost:8080/checkout?amount=10  //
    @GetMapping("/checkout")
    public String checkout(Model model, @RequestParam(required = true) Integer amount) {
        model.addAttribute("amount", amount*100);
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", Currency.EUR);
        return "checkout";
    }
}
