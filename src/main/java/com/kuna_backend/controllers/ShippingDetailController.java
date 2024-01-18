package com.kuna_backend.controllers;

import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.ShippingDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shippingdetail")
public class ShippingDetailController {

    @Autowired
    private ShippingDetailService shippingDetailService;

    @Autowired
    private AuthenticationService authenticationService;

    // GET Shipping Detail of all Clients - Endpoint
    @GetMapping("/all")
    public ResponseEntity<List<ShippingDetail>> getShippingDetails() {

        List<ShippingDetail> body = shippingDetailService.getAllShippingDetails();
        return new ResponseEntity<List<ShippingDetail>>(body, HttpStatus.OK);
    }

    // GET Shipping Detail for a Specific Client - Endpoint
    @GetMapping("/{id}")
    public ResponseEntity<ShippingDetail> getShippingDetailById (@PathVariable ("id") long id, @RequestParam("token") String token)
            throws AuthenticationFailException, ClassNotFoundException {

        authenticationService.authenticate(token);

        authenticationService.getClient(token);

        ShippingDetail shippingDetail = shippingDetailService.getShippingDetail(id);

        return new ResponseEntity<>(shippingDetail, HttpStatus.OK);
    }
}
