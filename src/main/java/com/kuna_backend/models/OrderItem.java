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

    @Column (name = "price", nullable = false)
    private Double price;

    @Column (name = "quantity", nullable = false)
    private Integer quantity;

    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // Many to One relationship with Order //
    @ManyToOne
    @JoinColumn (name = "order_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Order order;

    // One to one relationship with Item //
    @OneToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    public OrderItem(Integer id, Double price, Integer quantity, LocalDateTime createdAt, Order order, Item item) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.order = order;
        this.item = item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
