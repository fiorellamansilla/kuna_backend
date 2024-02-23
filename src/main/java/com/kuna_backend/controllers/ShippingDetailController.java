package com.kuna_backend.controllers;

import com.kuna_backend.exceptions.AuthenticationFailException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.services.AuthenticationService;
import com.kuna_backend.services.ShippingDetailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ShippingDetail> getShippingDetailById (
            @PathVariable ("id") Long id,
            @RequestParam("token") String token
    ) throws AuthenticationFailException, EntityNotFoundException {

        try {
            authenticationService.authenticate(token);
            Client client = authenticationService.getClient(token);

            Optional<ShippingDetail> shippingDetailOptional = shippingDetailService.getShippingDetail(client);

            if (shippingDetailOptional.isPresent()) {
                ShippingDetail shippingDetail = shippingDetailOptional.get();

                // Check if the retrieved shipping detail belongs to the authenticated client
                if (shippingDetail.getClient().equals(client)) {
                    return ResponseEntity.ok(shippingDetail);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
