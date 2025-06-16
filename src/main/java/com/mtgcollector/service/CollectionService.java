package com.mtgcollector.service;

import com.mtgcollector.dto.AddCardToCollectionDto;
import com.mtgcollector.dto.CollectionEntryDto;
import com.mtgcollector.model.Card;
import com.mtgcollector.model.CollectionEntry;
import com.mtgcollector.model.User;
import com.mtgcollector.repository.CardRepository;
import com.mtgcollector.repository.CollectionEntryRepository;
import com.mtgcollector.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.CardNotPresentException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional      // Ensures database consistency across multiple operations
public class CollectionService {

    @Autowired
    private CollectionEntryRepository collectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CardRepository cardRepository;

    public CollectionEntryDto addCardToCollection(String username, AddCardToCollectionDto dto) {
        // Business rule: Only authenticated users can add to their collection
        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Find card
        Card card = cardRepository.findById(dto.getCardId())
                .orElseThrow(() -> new CardNotPresentException("Card not found with id: " + dto.getCardId()));

        // Business rule: If card already exists in collection, update quantity instead of creating duplicate
        // Check if card already exists in collection
        Optional<CollectionEntry> existingEntry = collectionRepository.findByUserAndCard(user, card);

        if (existingEntry.isPresent()) {
            // Update existing entry
            CollectionEntry entry = existingEntry.get();
            entry.setQuantity(entry.getQuantity() + dto.getQuantity());
            entry.setCondition(dto.getCondition());
            entry.setNotes(dto.getNotes());
            entry = collectionRepository.save(entry);
            return convertToDto(entry);
        } else {
            // Create new entry
            CollectionEntry newEntry = new CollectionEntry();
            newEntry.setUser(user);
            newEntry.setCard(card);
            newEntry.setQuantity(dto.getQuantity());
            newEntry.setCondition(dto.getCondition());
            newEntry.setNotes(dto.getNotes());

            newEntry = collectionRepository.save(newEntry);
            return convertToDto(newEntry);
        }
    }

    public List<CollectionEntryDto> getUserCollection(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        List<CollectionEntry> entries = collectionRepository.findByUserOrderByAddedAtDesc(user);
        return entries.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void removeCardFromCollection(String username, Long cardId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotPresentException("Card not found: " + cardId));

        com.mtgcollector.dto.CollectionEntry entry = collectionRepository.findByUserAndCard(user, card).orElseThrow(() -> new CollectionEntryNotFoundException("Card not found in user's collection"));

        collectionRepository.delete(entry);
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
        return dto;
    }
}
