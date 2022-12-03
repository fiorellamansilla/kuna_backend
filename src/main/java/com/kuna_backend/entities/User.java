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
