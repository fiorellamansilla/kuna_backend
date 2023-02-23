package com.kuna_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.NumericBooleanConverter;

import java.time.LocalDateTime;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column (name = "username", length = 64, nullable = false)
    private String username;
    @Column (name = "password_hash", length = 64, nullable = false)
    private String passwordHash;
    @Column (name = "email", length = 64, nullable = false)
    private String email;
    @Column (name = "country", length = 64, nullable = false)
    private String country;
    @Column (name = "is_blocked", columnDefinition = "BIT(1)", nullable = false)
    @Convert (converter = NumericBooleanConverter.class)
    private Boolean isBlocked;
    @Column (name = "is_approved", columnDefinition = "BIT(1)", nullable = false)
    @Convert (converter = NumericBooleanConverter.class)
    private Boolean isApproved;
    @Column (name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    public User() {
    }

    public User(Integer id, String username, String passwordHash, String email, String country, Boolean isBlocked, Boolean isApproved, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.country = country;
        this.isBlocked = isBlocked;
        this.isApproved = isApproved;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty(value = "isBlocked")
    public Boolean getBlocked() {
        return isBlocked;
    }

    @JsonProperty(value = "isBlocked")
    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    @JsonProperty(value = "isApproved")
    public Boolean getApproved() {
        return isApproved;
    }

    @JsonProperty(value = "isApproved")
    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
