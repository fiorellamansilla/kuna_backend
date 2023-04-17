package com.kuna_backend.services;

import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Payment;
import com.kuna_backend.repositories.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Value("${BASE_URL}")
    private String baseURL;

    @Value("${STRIPE_SECRET_KEY}")
    private String apiKey;

    // Create total Price for an Order
    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("usd")
                .setUnitAmount((long)(checkoutItemDto.getPrice()*100))
                .setProductData(
                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(checkoutItemDto.getItemName())
                                .build())
                .build();
    }

    // Create Session from list of Checkout items
    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {

        // Supply Success and Failure url for Stripe
        String successURL = baseURL + "payment/success";
        String failureURL = baseURL + "payment/failed";

        // Set Private Key
        Stripe.apiKey = apiKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();

        // For each product compute SessionCreateParams.LineItem
        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
            sessionItemList.add(createSessionLineItem(checkoutItemDto));
        }

        // Build the session params
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl(failureURL)
                .addAllLineItem(sessionItemList)
                .setSuccessUrl(successURL)
                .build();
        return Session.create(params);
    }

    // Build each Item in the Stripe checkout page
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // Set price for each Item
                .setPriceData(createPriceData(checkoutItemDto))
                // Set quantity for each Item
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
                .build();
    }

    // Save Payment after Checkout session
    public void savePaymentFromSession(String stripeToken, Client client) throws StripeException {

        Stripe.apiKey = apiKey;
        Session session = Session.retrieve(stripeToken);

        // Create the Payment with the data retrieved from the Stripe session
        Payment payment = new Payment();
        payment.setAmount(session.getAmountTotal() / 100.0f);
        payment.setCurrency(session.getCurrency().toUpperCase());
        payment.setStripeToken(session.getId());
        payment.setPaymentStatus(session.getPaymentStatus().toUpperCase());
        payment.setProvider("Stripe");
        payment.setPaymentDate(new Date(session.getCreated() * 1000L));
        payment.setClient(client);
        // Save payment into the database
        paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    public Payment getPayment (String stripeToken) {
        return paymentRepository.findPaymentByStripeToken(stripeToken);
    }

    public void deletePayment (Integer id) {
        paymentRepository.deleteById(id);
    }
}
