package com.kuna_backend.services;

import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.exceptions.OrderNotFoundException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.models.OrderItem;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.repositories.OrderItemsRepository;
import com.kuna_backend.repositories.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void placeOrder (Client client, String sessionId, ShippingDetail shippingDetail) {

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

        // Set the shipping details for the Order
        newOrder.setShippingDetail(shippingDetail);
        orderRepository.save(newOrder);

        // Delete items from cart after the client has placed the order
        cartService.deleteClientCartItems(client);
    }

    public List<Order> listOrders(Client client) {
        return orderRepository.findAllByClientOrderByCreatedAtDesc(client);
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
