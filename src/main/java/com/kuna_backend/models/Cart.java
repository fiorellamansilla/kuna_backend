package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table (name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "quantity")
    private int quantity;

    @Column (name = "created_at")
    private Date createdAt;

    // Many-to-one relationship with Product Variation
    @ManyToOne
    @JoinColumn (name = "product_variation_id", referencedColumnName = "id")
    private ProductVariation productVariation;

    // One-to-one relationship with Client
    @OneToOne
    @JoinColumn (name = "client_id")
    @JsonIgnore
    private Client client;

    public Cart(ProductVariation productVariation, int quantity, Client client) {
        this.client = client;
        this.quantity = quantity;
        this.productVariation = productVariation;
        this.createdAt = new Date();
    }

    public Cart() {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ProductVariation getProductVariation() {
        return productVariation;
    }

    public void setProductVariation(ProductVariation productVariation) {
        this.productVariation = productVariation;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
