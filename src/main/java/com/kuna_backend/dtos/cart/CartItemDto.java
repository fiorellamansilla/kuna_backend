package com.kuna_backend.dtos.cart;

import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Product;
import com.kuna_backend.models.ProductVariation;

public class CartItemDto {
    private Long id;
    private int quantity;
    private Product product;
    private ProductVariation productVariation;

    public CartItemDto() {
    }

    public CartItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProductVariation().getProduct());
        this.setProductVariation(cart.getProductVariation());
    }

    @Override
    public String toString() {
        return "CartDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", productName=" + product.getName() +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
