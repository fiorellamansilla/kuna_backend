package com.kuna_backend.builders;

import com.kuna_backend.dtos.cart.CartDto;
import com.kuna_backend.dtos.cart.CartItemDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.Order;
import com.kuna_backend.models.Payment;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;
import com.kuna_backend.models.ShippingDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderTestDataBuilder {

    public static Order buildOrder(Client client, Payment payment, ShippingDetail shippingDetail) {
        Order order = new Order();
        order.setClient(client);
        order.setPayment(payment);
        order.setShippingDetail(shippingDetail);
        return order;
    }

    public static CartDto buildCartDto(List<CartItemDto> cartItems, double totalCost) {
        CartDto cartDto = new CartDto();
        cartDto.setCartItems(cartItems);
        cartDto.setTotalCost(totalCost);
        return cartDto;
    }

    public static CartItemDto buildCartItemDto(Long id, Product product, ProductVariation variation, int quantity) {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setId(id);
        cartItemDto.setProduct(product);
        cartItemDto.setProductVariation(variation);
        cartItemDto.setQuantity(quantity);
        return cartItemDto;
    }

    public static List<CartItemDto> buildCartItemDtoList() {
        List<CartItemDto> cartItems = new ArrayList<>();

        Product product1 = new Product();
        product1.setPrice(10.0);
        ProductVariation variation1 = new ProductVariation();
        variation1.setId(1L);
        variation1.setProduct(product1);
        variation1.setQuantityStock(5);
        CartItemDto cartItemDto1 = buildCartItemDto(1L, product1, variation1, 2);
        cartItems.add(cartItemDto1);

        Product product2 = new Product();
        product2.setPrice(15.0);
        ProductVariation variation2 = new ProductVariation();
        variation2.setId(2L);
        variation2.setProduct(product2);
        variation2.setQuantityStock(5);
        CartItemDto cartItemDto2 = buildCartItemDto(2L, product2, variation2, 1);
        cartItems.add(cartItemDto2);

        return cartItems;
    }
}