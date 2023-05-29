package com.kuna_backend;

import com.kuna_backend.dtos.cart.AddToCartDto;
import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.exceptions.CartItemNotExistException;
import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.repositories.CartRepository;
import com.kuna_backend.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddToCart() {

        AddToCartDto addToCartDto = new AddToCartDto();
        ProductVariation productVariation = new ProductVariation();
        Client client = new Client();

        Cart cart = new Cart(productVariation, addToCartDto.getQuantity(), client);

        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        cartService.addToCart(addToCartDto, productVariation, client);

        assertEquals(productVariation, cart.getProductVariation());
        assertEquals(addToCartDto.getQuantity(), cart.getQuantity());
        assertEquals(client, cart.getClient());
    }

    @Test
    public void testListCartItems() {

        Client client = new Client();

        Product product1 = new Product();
        product1.setPrice(10.0);

        Product product2 = new Product();
        product2.setPrice(20.0);

        ProductVariation productVariation1 = new ProductVariation();
        productVariation1.setProduct(product1);

        ProductVariation productVariation2 = new ProductVariation();
        productVariation2.setProduct(product2);;

        Cart cart1 = new Cart();
        cart1.setProductVariation(productVariation1);
        cart1.setQuantity(2);

        Cart cart2 = new Cart();
        cart2.setProductVariation(productVariation2);
        cart2.setQuantity(1);

        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart1);
        cartList.add(cart2);

        when(cartRepository.findAllByClientOrderByCreatedAtDesc(client)).thenReturn(cartList);

        CartItemDto cartItemDto1 = new CartItemDto();
        cartItemDto1.setProduct(cart1.getProductVariation().getProduct());
        cartItemDto1.setProductVariation(cart1.getProductVariation());
        cartItemDto1.setQuantity(cart1.getQuantity());

        CartItemDto cartItemDto2 = new CartItemDto();
        cartItemDto2.setProduct(cart2.getProductVariation().getProduct());
        cartItemDto2.setProductVariation(cart2.getProductVariation());
        cartItemDto2.setQuantity(cart2.getQuantity());

        // Perform the test
        CartDto cartDto = cartService.listCartItems(client);

        Assertions.assertEquals(2, cartDto.getCartItems().size());
        Assertions.assertEquals(2, cartDto.getCartItems().get(0).getQuantity());
        Assertions.assertEquals(1, cartDto.getCartItems().get(1).getQuantity());
        Assertions.assertEquals(40.0, cartDto.getTotalCost(), 0.0001);
    }

    @Test
    public void testDeleteCartItem_Exists() throws CartItemNotExistException {

        Integer id = 1;
        when(cartRepository.existsById(id)).thenReturn(true);

        cartService.deleteCartItem(id, 123);

        verify(cartRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteCartItem_NotExist() {

        Integer id = 1;
        when(cartRepository.existsById(id)).thenReturn(false);

        assertThrows(CartItemNotExistException.class, () -> cartService.deleteCartItem(id, 123));

        verify(cartRepository, never()).deleteById(id);
    }

    @Test
    public void testDeleteCartItems() {

        cartService.deleteCartItems(123);

        verify(cartRepository, times(1)).deleteAll();
    }

    @Test
    public void testDeleteClientCartItems() {

        Client client = new Client();

        cartService.deleteClientCartItems(client);

        verify(cartRepository, times(1)).deleteByClient(client);
    }

}
