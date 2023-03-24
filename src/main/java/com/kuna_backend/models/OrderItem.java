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

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @Column (name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "quantity", nullable = false)
    private Integer quantity;

    @Column (name = "price", nullable = false)
    private Double price;

    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Many-to-One relationship with Order //
    @ManyToOne
    @JoinColumn (name = "order_id", referencedColumnName = "id")
    @JsonIgnore
    private Order order;

    // One to one relationship with Item //
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    public OrderItem() {
    }

    public OrderItem (Item item,Integer quantity, Double price, Order order, LocalDateTime createdAt) {
        this.item = item;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
        this.createdAt = createdAt;
    }
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
