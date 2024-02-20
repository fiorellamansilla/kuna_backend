package com.kuna_backend.dtos.checkout;

import com.kuna_backend.models.Cart;

public class CheckoutItemDto {

    private String productName;
    private int quantity;
    private double price;
    private Long productVariationId;
    private Long clientId;

    public CheckoutItemDto() {
    }

    public CheckoutItemDto(Cart cart) {
        this.productName = cart.getProductVariation().getProduct().getName();
        this.quantity = cart.getQuantity();
        this.price = cart.getProductVariation().getProduct().getPrice();
        this.productVariationId = cart.getProductVariation().getId();
        this.clientId = cart.getClient().getId();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName (String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(Long productVariationId) {
        this.productVariationId = productVariationId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
