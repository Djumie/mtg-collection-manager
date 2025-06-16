package com.mtgcollector.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.annotation.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.engine.spi.CollectionEntry;
import org.hibernate.mapping.FetchProfile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Will be encrypted

    @Column(unique = true, nullable = false)
    private String email;

    @CreationTimestamp
    private LocalDateTime getGetUpdateAt;

    @UpdateTimestamp
    private LocalDateTime getUpdateAt;

    // One user can have many collections entries
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollectionEntry> collections = new ArrayList<>();

    // Constructors, getters, and setters
    public User() {}
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getGetGetUpdateAt() {
        return getGetUpdateAt;
    }

    public void setGetGetUpdateAt(LocalDateTime getGetUpdateAt) {
        this.getGetUpdateAt = getGetUpdateAt;
    }

    public LocalDateTime getGetUpdateAt() {
        return getUpdateAt;
    }

    public void setGetUpdateAt(LocalDateTime getUpdateAt) {
        this.getUpdateAt = getUpdateAt;
    }
}