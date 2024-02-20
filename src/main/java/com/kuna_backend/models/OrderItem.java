package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.Date;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "quantity", nullable = false)
    private int quantity;

    @Column (name = "price", nullable = false)
    private double price;

    @Column (name = "created_at", nullable = false)
    private Date createdAt;

    // Many-to-One relationship with Order //
    @ManyToOne
    @JoinColumn (name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    // One-to-one relationship with Product //
    @OneToOne
    @JoinColumn(name = "product_variation_id", referencedColumnName = "id")
    private ProductVariation productVariation;

    public OrderItem() {
    }

    public OrderItem (ProductVariation productVariation, int quantity, double price, Order order, Date createdAt) {
        this.productVariation = productVariation;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.createdAt = createdAt;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
