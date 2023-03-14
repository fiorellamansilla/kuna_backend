package com.kuna_backend.dtos.cart;

import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Item;

public class CartItemDto {
    private Integer id;
    private Integer quantity;
    private Item item;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setItem(cart.getItem());
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", itemName=" + item.getName() +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
