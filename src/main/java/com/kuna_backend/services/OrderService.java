package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.dtos.checkout.CheckoutItemDto;
import com.kuna_backend.exceptions.OrderNotFoundException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.models.OrderItem;
import com.kuna_backend.repositories.OrderItemsRepository;
import com.kuna_backend.repositories.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {

    @Autowired
    private CartService cartService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemsRepository orderItemsRepository;

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

    // Build each Item in the Stripe checkout page
    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
        return SessionCreateParams.LineItem.builder()
                // Set price for each Item
                .setPriceData(createPriceData(checkoutItemDto))
                // Set quantity for each Item
                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
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

    public void placeOrder (Client client, String sessionId) {

        // Retrieve cart items for the Client
        CartDto cartDto = cartService.listCartItems(client);

        List<CartItemDto> cartItemDtoList = cartDto.getCartItems();

        // Create the Order and Save it
        Order newOrder = new Order();
        newOrder.setCreatedAt(new Date());
        newOrder.setSessionId(sessionId);
        newOrder.setClient(client);
        newOrder.setTotalAmount(cartDto.getTotalCost());
        orderRepository.save(newOrder);

        for (CartItemDto cartItemDto : cartItemDtoList) {
            // Create OrderItem and Save each one
            OrderItem orderItem = new OrderItem();
            orderItem.setCreatedAt(new Date());
            orderItem.setPrice(cartItemDto.getItem().getPrice());
            orderItem.setItem(cartItemDto.getItem());
            orderItem.setQuantity(cartItemDto.getQuantity());
            orderItem.setOrder(newOrder);

            orderItemsRepository.save(orderItem);
        }

        // Delete items from cart after the client has placed the order
        cartService.deleteClientCartItems(client);
    }

    public List<Order> listOrders(Client client) {
        return orderRepository.findAllByClientOrderByCreatedDateDesc(client);
    }

    public Order getOrder (Integer orderId) throws OrderNotFoundException {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()) {
            return order.get();
        }
        throw new OrderNotFoundException("Order not found");
    }

    public void deleteOrder (Integer id) {
        orderRepository.deleteById(id);
    }
}
