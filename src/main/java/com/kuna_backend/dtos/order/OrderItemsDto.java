package com.kuna_backend.dtos.order;

import org.jetbrains.annotations.NotNull;

public class OrderItemsDto {

    private @NotNull double price;
    private @NotNull int quantity;
    private @NotNull int orderId;
    private @NotNull int itemId;

    public OrderItemsDto() {
    }

    public OrderItemsDto(@NotNull double price, @NotNull int quantity, @NotNull int orderId, @NotNull int itemId) {
        this.price = price;
        this.quantity = quantity;
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
