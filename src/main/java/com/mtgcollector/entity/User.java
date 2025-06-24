package com.mtgcollector.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @CreationTimestamp
    private LocalDateTime getGetUpdateAt;

    @UpdateTimestamp
    private LocalDateTime getUpdateAt;

    // One user can have many collections entries
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CollectionEntry> collectionsEntries = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Constructors


    public User(Long id, String username, String password, String email) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public List<CollectionEntry> getCollectionsEntries() {
        return collectionsEntries;
    }

    public void setCollectionsEntries(List<CollectionEntry> collectionsEntries) {
        this.collectionsEntries = collectionsEntries;
    }
}

enum Role {
    USER, ADMIN
}