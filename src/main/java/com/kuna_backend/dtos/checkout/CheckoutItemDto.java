package com.kuna_backend.dtos.checkout;

public class CheckoutItemDto {

    private String itemName;
    private int quantity;
    private double price;
    private int productVariationId;
    private int clientId;

    public CheckoutItemDto() {
    }

    public CheckoutItemDto(String itemName, int quantity, double price, int productVariationId, int clientId) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.productVariationId = productVariationId;
        this.clientId = clientId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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

    public int getProductVariationId() {
        return productVariationId;
    }

    public void setProductVariationId(int productVariationId) {
        this.productVariationId = productVariationId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
