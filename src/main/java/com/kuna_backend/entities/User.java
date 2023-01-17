package com.kuna_backend.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table (name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column (name = "username", length = 64, nullable = false)
    private String username;
    @Column (name = "password_hash", length = 64, nullable = false)
    private String passwordHash;
    @Column (name = "email", length = 64, nullable = false)
    private String email;
    @Column (name = "country", length = 64, nullable = false)
    private String country;
    @Column (name = "is_blocked", columnDefinition = "bit", nullable = false)
    private Boolean isBlocked;
    @Column (name = "is_approved", columnDefinition = "bit", nullable = false)
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

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public Boolean getApproved() {
        return isApproved;
    }

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
