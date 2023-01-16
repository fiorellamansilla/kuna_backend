package com.kuna_backend.entities;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity (name = "users")
public class User {
    private Integer id;
    private String username;
    private String passwordHash;
    private String email;
    private String country;
    private Boolean isBlocked;
    private Boolean isApproved;
    private LocalDateTime createdAt;

    public User(Integer id,
                String username,
                String passwordHash,
                String email, String country,
                Boolean isBlocked,
                Boolean isApproved,
                LocalDateTime createdAt)
    {
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

    public Boolean getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(Boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", isBlocked=" + isBlocked +
                ", isApproved=" + isApproved +
                ", createdAt=" + createdAt +
                '}';
    }
}
