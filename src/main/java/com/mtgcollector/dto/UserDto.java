package com.mtgcollector.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// For user registration requests
public class CreateUserDto {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    // Constructors

    public CreateUserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters

    public @NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank(message = "Username is required") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters") String username) {
        this.username = username;
    }

    public @NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters") String password) {
        this.password = password;
    }

    public @NotBlank(message = "Email is required") @Email(message = "Email must be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank(message = "Email is required") @Email(message = "Email must be valid") String email) {
        this.email = email;
    }
}

// For user responses (never include password)
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private int totalCards;         // Calculated field
    private BigDecimal collectionValue; // Calculated field

    // Constructors

    public UserDto(Long id, String username, String email, LocalDateTime createdAt, int totalCards, BigDecimal collectionValue) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
        this.totalCards = totalCards;
        this.collectionValue = collectionValue;
    }

    // Getters and Setters

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

    public int getTotalCards() {
        return totalCards;
    }

    public void setTotalCards(int totalCards) {
        this.totalCards = totalCards;
    }

    public BigDecimal getCollectionValue() {
        return collectionValue;
    }

    public void setCollectionValue(BigDecimal collectionValue) {
        this.collectionValue = collectionValue;
    }
}

// For authentication responses
public class AuthResponseDto {
    private String token;
    private UserDto user;
    private String tokenType = "Bearer";
    private long expiresIn;

    //Constructors

    public AuthResponseDto(String token, UserDto user, String tokenType, long expiresIn) {
        this.token = token;
        this.user = user;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    // Getters and Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }
}
