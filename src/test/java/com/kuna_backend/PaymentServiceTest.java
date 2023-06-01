package com.kuna_backend;

import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Payment;
import com.kuna_backend.repositories.PaymentRepository;
import com.kuna_backend.services.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    public Session session;

    @Value("${STRIPE_SECRET_KEY}")
    public String apiKey;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Stripe.apiKey = apiKey;
    }

    @Test
    void createPriceData_ShouldCreatePriceDataSuccessfully() {

        CheckoutItemDto checkoutItemDto = new CheckoutItemDto();
        checkoutItemDto.setProductName("Product 1");
        checkoutItemDto.setPrice(10.0);
        checkoutItemDto.setQuantity(2);

        SessionCreateParams.LineItem.PriceData result = paymentService.createPriceData(checkoutItemDto);

        assertEquals("usd", result.getCurrency());
        assertEquals(1000L, result.getUnitAmount());
        assertEquals("Product 1", result.getProductData().getName());
    }

    @Test
    void createSessionLineItem_ShouldCreateSessionLineItemSuccessfully() {

        CheckoutItemDto checkoutItemDto = new CheckoutItemDto();
        checkoutItemDto.setProductName("Product 1");
        checkoutItemDto.setPrice(10.0);
        checkoutItemDto.setQuantity(2);

        SessionCreateParams.LineItem result = paymentService.createSessionLineItem(checkoutItemDto);

        assertEquals("usd", result.getPriceData().getCurrency());
        assertEquals(1000L, result.getPriceData().getUnitAmount());
        assertEquals(2, result.getQuantity());
    }

    @Test
    public void createSession_ShouldCreateSessionSuccessfully() throws StripeException {

        CheckoutItemDto checkoutItemDto = new CheckoutItemDto();
        checkoutItemDto.setProductName("Product 1");
        checkoutItemDto.setPrice(10.0);
        checkoutItemDto.setQuantity(2);

        String successURL = "http://localhost:8080/payment/success";
        String failureURL = "http://localhost:8080/payment/failed";

        // Mock Price Data Builder
        SessionCreateParams.LineItem.PriceData.Builder priceDataBuilder =
                mock(SessionCreateParams.LineItem.PriceData.Builder.class);
        when(priceDataBuilder.setCurrency("usd")).thenReturn(priceDataBuilder);
        when(priceDataBuilder.setUnitAmount(1000L)).thenReturn(priceDataBuilder);

        SessionCreateParams.LineItem.PriceData priceData = mock(SessionCreateParams.LineItem.PriceData.class);
        when(priceDataBuilder.build()).thenReturn(priceData);

        // Mock Session Line Item Builder
        SessionCreateParams.LineItem.Builder sessionLineItemBuilder =
                mock(SessionCreateParams.LineItem.Builder.class);
        when(sessionLineItemBuilder.setPriceData(priceData)).thenReturn(sessionLineItemBuilder);
        when(sessionLineItemBuilder.setQuantity(2L)).thenReturn(sessionLineItemBuilder);

        SessionCreateParams.LineItem sessionLineItem = mock(SessionCreateParams.LineItem.class);
        when(sessionLineItemBuilder.build()).thenReturn(sessionLineItem);

        SessionCreateParams.Builder paramsBuilder = mock(SessionCreateParams.Builder.class);
        when(paramsBuilder.addPaymentMethodType(any())).thenReturn(paramsBuilder);
        when(paramsBuilder.setMode(any())).thenReturn(paramsBuilder);
        when(paramsBuilder.setCancelUrl(any())).thenReturn(paramsBuilder);
        when(paramsBuilder.addAllLineItem(any())).thenReturn(paramsBuilder);
        when(paramsBuilder.setSuccessUrl(any())).thenReturn(paramsBuilder);

        SessionCreateParams params = mock(SessionCreateParams.class);
        when(paramsBuilder.build()).thenReturn(params);

        try (MockedStatic<SessionCreateParams.LineItem.PriceData> priceDataBuilderMock =
                     mockStatic(SessionCreateParams.LineItem.PriceData.class);
             MockedStatic<SessionCreateParams.LineItem> lineItemBuilderMock =
                     mockStatic(SessionCreateParams.LineItem.class);
             MockedStatic<SessionCreateParams> sessionBuilderMock =
                     mockStatic(SessionCreateParams.class)) {

            priceDataBuilderMock.when(SessionCreateParams.LineItem.PriceData::builder).thenReturn(priceDataBuilder);
            lineItemBuilderMock.when(SessionCreateParams.LineItem::builder).thenReturn(sessionLineItemBuilder);
            sessionBuilderMock.when(SessionCreateParams::builder).thenReturn(paramsBuilder);

            when(paramsBuilder.setSuccessUrl(successURL)).thenReturn(paramsBuilder);
            when(Session.create(params)).thenReturn(session);

            Session result = paymentService.createSession(List.of(checkoutItemDto));

            assertEquals(session, result);
            verify(paymentService).createPriceData(checkoutItemDto);
            verify(paymentService).createSessionLineItem(checkoutItemDto);
            verify(paramsBuilder).addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
            verify(paramsBuilder).setMode(SessionCreateParams.Mode.PAYMENT);
            verify(paramsBuilder).setCancelUrl(failureURL);
            verify(paramsBuilder).addAllLineItem(List.of(sessionLineItem));
            verify(paramsBuilder).setSuccessUrl(successURL);
            verify(session).create(params);
        }
    }

    @Test
    void savePaymentFromSession_ShouldSavePaymentSuccessfully() throws StripeException {
        
        String stripeToken = "test_token";
        when(Session.retrieve(stripeToken)).thenReturn(session);

        when(session.getAmountTotal()).thenReturn(1000L);
        when(session.getCurrency()).thenReturn("usd");
        when(session.getId()).thenReturn("session_id");
        when(session.getPaymentStatus()).thenReturn("paid");
        when(session.getCreated()).thenReturn(System.currentTimeMillis() / 1000L);

        Client client = new Client();

        paymentService.savePaymentFromSession(stripeToken, client);

        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    public void testGetAllPayments() {
        List<Payment> paymentList = new ArrayList<>();
        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(paymentList, result);
        verify(paymentRepository).findAll();
    }

    @Test
    public void testGetPaymentById() {
        String stripeToken = "test_token";
        Payment payment = new Payment();
        when(paymentRepository.findPaymentByStripeToken(stripeToken)).thenReturn(payment);

        Payment result = paymentService.getPaymentById(stripeToken);

        assertEquals(payment, result);
        verify(paymentRepository).findPaymentByStripeToken(stripeToken);
    }
}
