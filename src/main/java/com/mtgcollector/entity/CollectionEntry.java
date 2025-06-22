package com.mtgcollector.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "collection_entries")
public class CollectionEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)      // Many entries can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)      // Many entries can reference one card
    @JoinColumn(name = "card_id", nullable = false)
    private Card card;

    @Column(nullable = false)               // How many copies the user owns
    private Integer quantity = 1;

    @Column(name = "added_at")
    private LocalDateTime addedAt;          //When added to collection

    @Column(columnDefinition = "TEXT")
    private String notes;                   // Personal notes about the card
    private String condition;               // Near Mint, Lightly Played, etc.

    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }

    // Constructors
    public CollectionEntry() {}

    public CollectionEntry(User user, Card card, Integer quantity) {
        this.user = user;
        this.card = card;
        this.quantity = quantity;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}