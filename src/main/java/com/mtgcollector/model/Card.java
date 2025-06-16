package com.mtgcollector.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.engine.spi.CollectionEntry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String type;
    private String manaCost;
    private String setName;
    private String imageUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal marketPrice;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // One card can be in many collections
    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY)
    private List<CollectionEntry> collectionEntries = new ArrayList<>();

    // Constructors

    private Card(String name, String type, String manaCost, String setName) {
        this.name = name;
        this.type = type;
        this.manaCost = manaCost;
        this.setName = setName;
    }

    public static Card createCard(String name, String type, String manaCost, String setName) {
        return new Card(name, type, manaCost, setName);
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<CollectionEntry> getCollectionEntries() {
        return collectionEntries;
    }

    public void setCollectionEntries(List<CollectionEntry> collectionEntries) {
        this.collectionEntries = collectionEntries;
    }
}