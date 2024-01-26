package com.kuna_backend;

import com.kuna_backend.builders.OrderTestDataBuilder;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.enums.OrderStatus;
import com.kuna_backend.exceptions.OrderNotFoundException;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.models.OrderItem;
import com.kuna_backend.models.Payment;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.repositories.OrderItemsRepository;
import com.kuna_backend.repositories.OrderRepository;
import com.kuna_backend.repositories.ProductVariationRepository;
import com.kuna_backend.services.CartService;
import com.kuna_backend.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CartService cartService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductVariationRepository productVariationRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testPlaceOrder_ShouldCreateNewOrderWithCorrectDetails() {

        Client client = new Client();
        Payment payment = new Payment();
        ShippingDetail shippingDetail = new ShippingDetail();

        List<CartItemDto> cartItemDtoList = OrderTestDataBuilder.buildCartItemDtoList();
        CartDto cartDto = OrderTestDataBuilder.buildCartDto(cartItemDtoList, 100.0);

        when(cartService.listCartItems(client)).thenReturn(cartDto);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            if (order.getId() == 0) {
                // Set the ID manually if it's not set (first invocation)
                order.setId(1L);
            }
            return order;
        });

        orderService.placeOrder(client, payment, shippingDetail);

        // Verify the order creation with correct details
        verify(orderRepository, times(2)).save(argThat(order -> {
            assertNotNull(order.getCreatedAt());
            assertEquals(client, order.getClient());
            assertEquals(payment, order.getPayment());
            assertEquals(cartDto.getTotalCost(), order.getTotalAmount());
            assertEquals(OrderStatus.CONFIRMED, order.getOrderStatus());
            assertEquals(shippingDetail, order.getShippingDetail());
            return true;
        }));
    }

    @Test
    public void testPlaceOrder_ShouldCreateOrderItemsForCartItems() {

        Client client = new Client();
        Payment payment = new Payment();
        ShippingDetail shippingDetail = new ShippingDetail();

        List<CartItemDto> cartItemDtoList = OrderTestDataBuilder.buildCartItemDtoList();
        CartDto cartDto = OrderTestDataBuilder.buildCartDto(cartItemDtoList, 100.0);


        when(cartService.listCartItems(client)).thenReturn(cartDto);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(orderItemsRepository.save(any(OrderItem.class))).thenAnswer(invocation -> invocation.getArgument(0));

        orderService.placeOrder(client, payment, shippingDetail);

        // Verify the creation of order items for each cart item
        verify(orderItemsRepository, times(cartItemDtoList.size())).save(argThat(orderItem -> {
            assertNotNull(orderItem.getCreatedAt());
            assertNotNull(orderItem.getPrice());
            assertNotNull(orderItem.getProductVariation());
            assertNotNull(orderItem.getQuantity());
            assertNotNull(orderItem.getOrder());
            return true;
        }));
    }

    @Test
    public void testPlaceOrder_ShouldDeleteCartItemsAfterOrderPlacement() {

        Client client = new Client();
        Payment payment = new Payment();
        ShippingDetail shippingDetail = new ShippingDetail();

        List<CartItemDto> cartItemDtoList = OrderTestDataBuilder.buildCartItemDtoList();
        CartDto cartDto = OrderTestDataBuilder.buildCartDto(cartItemDtoList, 100.0);

        when(cartService.listCartItems(client)).thenReturn(cartDto);

        orderService.placeOrder(client, payment, shippingDetail);

        verify(cartService).deleteClientCartItems(client);
    }

    @Test
    public void testListOrders_ShouldReturnAllOrdersForClient() {

        Client client = new Client();
        List<Order> expectedOrders = new ArrayList<>();

        when(orderRepository.findAllByClientOrderByCreatedAtDesc(client)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.listOrders(client);

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testListOrdersByStatus_ShouldReturnAllOrdersWithGivenStatus() {

        OrderStatus orderStatus = OrderStatus.CONFIRMED;
        List<Order> expectedOrders = new ArrayList<>();

        when(orderRepository.findAllByOrderStatus(orderStatus)).thenReturn(expectedOrders);

        List<Order> actualOrders = orderService.listOrdersByStatus(orderStatus);

        assertEquals(expectedOrders, actualOrders);
    }

    @Test
    public void testGetOrder_ShouldReturnOrderById() throws OrderNotFoundException {

        Long orderId = 1L;
        Order expectedOrder = new Order();

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = orderService.getOrder(orderId);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void testGetOrder_ShouldThrowOrderNotFoundExceptionForInvalidId() {

        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(orderId));
    }

    @Test
    public void testDeleteOrder_ShouldDeleteOrderById() {

        Long orderId = 1L;

        orderService.deleteOrder(orderId);

        verify(orderRepository).deleteById(orderId);
    }
}