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
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PaymentServiceTest {
    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    public Session session;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        Stripe.apiKey = "sk_test_51MaxUkEyNC1M1bEEpJcQrzYAcde8ST7cCT4NqohGmnyPz6OWsWQxWFbcUFbX2pytE5JjVeopRcBeh8RxPObdsmcz009C6OlGHh";
        paymentService.baseURL = "http://localhost:8080/";
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

        String successURL = paymentService.baseURL + "payment/success";
        String failureURL = paymentService.baseURL + "payment/failed";

        SessionCreateParams.Builder paramsBuilder = mock(SessionCreateParams.Builder.class);
        SessionCreateParams params = mock(SessionCreateParams.class);
        Session session = mock(Session.class);
        List<CheckoutItemDto> checkoutItemDtoList = new ArrayList<>();

        // Mock the static methods
        try (MockedStatic<SessionCreateParams> sessionBuilderMock = mockStatic(SessionCreateParams.class);
             MockedStatic<Session> sessionMock = mockStatic(Session.class);
             MockedConstruction<SessionCreateParams.LineItem> lineItemConstruction = mockConstruction(SessionCreateParams.LineItem.class)) {

            // Stub the builder methods
            when(SessionCreateParams.builder()).thenReturn(paramsBuilder);
            when(paramsBuilder.addPaymentMethodType(any())).thenReturn(paramsBuilder);
            when(paramsBuilder.setMode(any())).thenReturn(paramsBuilder);
            when(paramsBuilder.setCancelUrl(eq(failureURL))).thenReturn(paramsBuilder);
            when(paramsBuilder.setSuccessUrl(eq(successURL))).thenReturn(paramsBuilder);
            when(paramsBuilder.addAllLineItem(any())).thenReturn(paramsBuilder);
            when(paramsBuilder.build()).thenReturn(params);

            // Stub the Session.create method
            sessionMock.when(() -> Session.create(params)).thenReturn(session);

            Session result = paymentService.createSession(checkoutItemDtoList);

            assertEquals(session, result);
            verify(paramsBuilder).addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
            verify(paramsBuilder).setMode(SessionCreateParams.Mode.PAYMENT);
            verify(paramsBuilder).setCancelUrl(failureURL);
            verify(paramsBuilder).addAllLineItem(any());
            verify(paramsBuilder).setSuccessUrl(successURL);
            verify(paramsBuilder).build();

            // Verify the static method call
            sessionMock.verify(() -> Session.create(params));

        }
    }

    @Test
    public void savePaymentFromSession_ShouldSavePaymentSuccessfully() throws StripeException {

        String stripeToken = "cs_test_a14LmWmbRX68zuH7z45k2cosRfWoq5sTE1srkhuekLlzhHzgI1Hu6Dqh9M";

        // Mock the static method Session.retrieve
        try (MockedStatic<Session> sessionMock = mockStatic(Session.class)) {
            sessionMock.when(() -> Session.retrieve(stripeToken)).thenReturn(session);
            when(session.getAmountTotal()).thenReturn(1000L);
            when(session.getCurrency()).thenReturn("usd");
            when(session.getId()).thenReturn(stripeToken);
            when(session.getPaymentStatus()).thenReturn("paid");
            when(session.getCreated()).thenReturn(System.currentTimeMillis() / 1000L);

            Client clientMock = mock(Client.class);

            paymentService.savePaymentFromSession(stripeToken, clientMock);

            // Verify that the paymentRepository.save method was called with the expected Payment object
            verify(paymentRepository).save(argThat(payment ->
                    payment.getAmount() == 10.0f &&
                            payment.getCurrency().equals("USD") &&
                            payment.getStripeToken().equals(stripeToken) &&
                            payment.getPaymentStatus().equals("PAID") &&
                            payment.getProvider().equals("Stripe") &&
                            payment.getClient() == clientMock
            ));
        }
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
