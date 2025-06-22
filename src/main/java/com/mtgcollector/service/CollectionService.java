package com.mtgcollector.service;

import com.mtgcollector.dto.CollectionEntryDto;
import com.mtgcollector.entity.Card;
import com.mtgcollector.entity.CollectionEntry;
import com.mtgcollector.entity.User;
import com.mtgcollector.exception.CardNotFoundException;
import com.mtgcollector.exception.UserNotFoundException;
import com.mtgcollector.repository.CardRepository;
import com.mtgcollector.repository.CollectionEntryRepository;
import com.mtgcollector.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional      // Ensures database consistency across multiple operations
public class CollectionService {

    private final CollectionEntryRepository collectionRepository;

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    public List<CollectionEntryDto> getUserCollection(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        List<CollectionEntry> entries = collectionRepository.findByUserOrderByAddedAtDesc(user);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public CollectionEntryDto addCardToCollection(String username, Long cardId, CollectionEntryDto.AddCardToCollectionDto dto) {
        // Business rule: Only authenticated users can add to their collection
        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        // Find card
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new CardNotFoundException("Card not found with id: " + dto.getCardId()));

        // Business rule: If card already exists in collection, update quantity instead of creating duplicate
        // Check if card already exists in collection
        Optional<CollectionEntry> existingEntry = collectionRepository.findByUserAndCard(user, card);

        CollectionEntry entry;
        if (existingEntry.isPresent()) {
            // Update existing entry
            entry = existingEntry.get();
            entry.setQuantity(entry.getQuantity() + dto.getQuantity());
            entry.setCondition(dto.getCondition());
            entry.setNotes(dto.getNotes());
        } else {
            // Create new entry
            entry = new CollectionEntry();
            entry.setUser(user);
            entry.setCard(card);
            entry.setQuantity(dto.getQuantity());
            entry.setCondition(dto.getCondition());
            entry.setNotes(dto.getNotes());
        }

        entry = collectionRepository.save(entry);
        return convertToDto(entry);
    }

    public void removeCardFromCollection(String username, Long entryId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        CollectionEntry entry = collectionRepository.findById(entryId)
                .orElseThrow(() -> new RuntimeException("Card not found in user's collection"));

        if (!entry.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }

        collectionRepository.delete(entry);
    }

    public BigDecimal getCollectionValue(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        return collectionRepository.calculateCollectionValue(user);
    }

    // Convert entity to DTO to avoid exposing internal database structure
    private CollectionEntryDto convertToDto(CollectionEntry entry) {
        CollectionEntryDto dto = new CollectionEntryDto();
        dto.setId(entry.getId());
        dto.setCardId(entry.getCard().getId());
        dto.setCardName(entry.getCard().getName());
        dto.setCardType(entry.getCard().getType());
        dto.setCardImageUrl(entry.getCard().getImageUrl());
        dto.setQuantity(entry.getQuantity());
        dto.setCondition(entry.getCondition());
        dto.setNotes(entry.getNotes());
        dto.setAddedAt(entry.getAddedAt());

        if (entry.getCard().getMarketPrice() != null) {
            dto.setTotalValue(entry.getCard().getMarketPrice().multiply(new BigDecimal(entry.getQuantity())));
        }
        return dto;
    }

    // Single constructor
    public CollectionService(CollectionEntryRepository collectionRepository, UserRepository userRepository, CardRepository cardRepository) {
        this.collectionRepository = collectionRepository;
        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }
}
