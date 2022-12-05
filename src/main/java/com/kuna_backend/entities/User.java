package com.kuna_backend.entities;

import java.time.LocalDateTime;

public class User {
    private Integer id;
    private String username;
    private String password_hash;
    private String email;
    private String country;
    private Boolean is_blocked;
    private Boolean is_approved;
    private LocalDateTime created_at;

    public User(Integer id,
                String username,
                String password_hash,
                String email, String country,
                Boolean is_blocked,
                Boolean is_approved,
                LocalDateTime created_at)
    {
        this.id = id;
        this.username = username;
        this.password_hash = password_hash;
        this.email = email;
        this.country = country;
        this.is_blocked = is_blocked;
        this.is_approved = is_approved;
        this.created_at = created_at;
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

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
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

    public Boolean getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(Boolean is_blocked) {
        this.is_blocked = is_blocked;
    }

    public Boolean getIs_approved() {
        return is_approved;
    }

    public void setIs_approved(Boolean is_approved) {
        this.is_approved = is_approved;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password_hash='" + password_hash + '\'' +
                ", email='" + email + '\'' +
                ", country='" + country + '\'' +
                ", is_blocked=" + is_blocked +
                ", is_approved=" + is_approved +
                ", created_at=" + created_at +
                '}';
    }
}
