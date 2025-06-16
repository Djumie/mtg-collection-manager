package com.mtgcollector.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

// For creating new cards (admin only)
public class CreateCardDto {
    @NotBlank(message = "Card name is required")
    private String name;
    private String type;
    private String manaCost;
    private String setName;
    private String imageUrl;
    private String description;

    @DecimalMin(value = "0.0", message = "Price cannot be negative")
    private BigDecimal marketPrice;

    // Constructors

    public CreateCardDto(String name, String type, String manaCost, String setName, String imageUrl, String description, BigDecimal marketPrice) {
        this.name = name;
        this.type = type;
        this.manaCost = manaCost;
        this.setName = setName;
        this.imageUrl = imageUrl;
        this.description = description;
        this.marketPrice = marketPrice;
    }

    // getters and Setters

    public @NotBlank(message = "Card name is required") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Card name is required") String name) {
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

    public @DecimalMin(value = "0.0", message = "Price cannot be negative") BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(@DecimalMin(value = "0.0", message = "Price cannot be negative") BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
}

public class CardDto {
    private Long id;
    private String name;
    private String type;
    private String manaCost;
    private String setName;
    private String imageUrl;
    private String description;
    private BigDecimal marketPrice;
    private boolean inCollection;       // Whether current user owns this card
    private int ownedQuantity;          // How many copies current user owns

    // Constructors

    public CardDto(Long id, String name, String type, String manaCost, String setName, String imageUrl, String description) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.manaCost = manaCost;
        this.setName = setName;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public boolean isInCollection() {
        return inCollection;
    }

    public void setInCollection(boolean inCollection) {
        this.inCollection = inCollection;
    }

    public int getOwnedQuantity() {
        return ownedQuantity;
    }

    public void setOwnedQuantity(int ownedQuantity) {
        this.ownedQuantity = ownedQuantity;
    }
}
