package com.kuna_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
@Table (name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "quantity")
    private Integer quantity;

    @Column (name = "created_at")
    private Date createdAt;

    // Many-to-one relationship with Item
    @ManyToOne
    @JoinColumn (name = "item_id")
    private Item item;

    // Many-to-one relationship with Client
    @ManyToOne
    @JoinColumn (name = "client_id")
    private Client client;

    public Cart() {
    }
    public Cart(Item item, Integer quantity, Client client) {
        this.client = client;
        this.quantity = quantity;
        this.item = item;
        this.createdAt = new Date();
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
