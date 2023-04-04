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

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Entity
@Table (name = "token")
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "token", length = 256)
    private String token;
    @Column (name = "created_at")
    private Date createdAt;
    @OneToOne (targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "client_id")
    private Client client;

    public AuthenticationToken(Client client) {
        this.client = client;
        this.createdAt = new Date();
        this.token = UUID.randomUUID().toString();
    }

    public AuthenticationToken() {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public AuthenticationToken(Integer id, String token, Date createdAt, Client client) {
        this.id = id;
        this.token = token;
        this.createdAt = createdAt;
        this.client = client;
    }
}
