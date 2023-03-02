package com.kuna_backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table (name = "token")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "token", length = 256, nullable = false)
    private String token;

    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne (targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn (name = "client_id", nullable = false)
    private Client client;

    public AuthenticationToken() {
    }

    public AuthenticationToken(Integer id, String token, LocalDateTime createdAt, Client client) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AuthenticationToken(Client client, LocalDateTime createdAt) {
        this.client = client;
        this.token = UUID.randomUUID().toString();
        this.createdAt = createdAt;
    }
}
