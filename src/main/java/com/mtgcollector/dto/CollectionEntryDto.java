package com.mtgcollector.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// For adding cards to collection
public class AddCardToCollectionDto {
    @NotNull(message = "Card ID is required")
    private Long cardId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity = 1;
    private String condition = "Near Mint";
    private String notes;

    // Constructors

    public AddCardToCollectionDto(Long cardId, Integer quantity, String condition, String notes) {
        this.cardId = cardId;
        this.quantity = quantity;
        this.condition = condition;
        this.notes = notes;
    }

    // Getters and Setters


    public @NotNull(message = "Card ID is required") Long getCardId() {
        return cardId;
    }

    public void setCardId(@NotNull(message = "Card ID is required") Long cardId) {
        this.cardId = cardId;
    }

    public @Min(value = 1, message = "Quantity must be at least 1") Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(value = 1, message = "Quantity must be at least 1") Integer quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

// For collection entry responses
public class CollectionEntryDto {
    private Long id;
    private Long cardId;
    private String cardName;
    private String cardImageUrl;
    private String cardType;
    private BigDecimal cardMarketPrice;
    private int quantity;
    private String condition;
    private String notes;
    private LocalDateTime addedAt;
    private BigDecimal totalValue;  // quantity * cardMarketPrice

    // Constructors

    public CollectionEntryDto(Long id, Long cardId, String cardName, String cardType, int quantity, String condition, String notes) {
        this.id = id;
        this.cardId = cardId;
        this.cardName = cardName;
        this.cardType = cardType;
        this.quantity = quantity;
        this.condition = condition;
        this.notes = notes;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImageUrl() {
        return cardImageUrl;
    }

    public void setCardImageUrl(String cardImageUrl) {
        this.cardImageUrl = cardImageUrl;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public BigDecimal getCardMarketPrice() {
        return cardMarketPrice;
    }

    public void setCardMarketPrice(BigDecimal cardMarketPrice) {
        this.cardMarketPrice = cardMarketPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }
}
