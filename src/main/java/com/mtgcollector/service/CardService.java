package com.mtgcollector.service;

import com.mtgcollector.dto.CardDto;
import com.mtgcollector.dto.CollectionEntry;
import com.mtgcollector.model.Card;
import com.mtgcollector.repository.CardRepository;
import com.mtgcollector.repository.CollectionEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CollectionEntryRepository collectionEntryRepository;

    public Page<CardDto> getAllCards(Pageable pageable) {
        Page<Card> cards = cardRepository.findAll(pageable);
        return cards.map(this::convertToDto);
    }

    public List<CardDto> searchCards(String name, String type, String setName) {
        List<Card> cards = cardRepository.searchCards(name, type, setName);
        return cards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Advanced search with multiple criteria and current user context
    public List<CardDto> searchCardsForUser(String name, String type, String setName, String username, boolean inCollectionOnly) {
        List<Card> cards;

        if (inCollectionOnly && username != null) {
            // Complex query to find cards in user's collection matching criteria
            cards = cardRepository.findCardsInUserCollection(username, name, type, setName);
        } else {
            cards = cardRepository.searchCards(name, type, setName);
        }

        return cards.stream()
                .map(card -> convertToDtoWithUserContext(card, username))
                .collect(Collectors.toList());
    }

    private CardDto convertToDtoWithUserContext(Card card, String username) {
        CardDto dto = convertToDto(card);

        if (username != null) {
            // Check if current user owns this card
            Optional<CollectionEntry> entry = collectionEntryRepository
                    .findByUserAndCard(username, card);

            if (entry.isPresent()) {
                dto.setInCollection(true);
                dto.setOwnedQuantity(entry.get().getQuantity());
            }
        }

        return dto;
    }

    private CardDto convertToDto(Card card) {
        CardDto dto = new CardDto();
        dto.setId(card.getId());
        dto.setName(card.getName());
        dto.setType(card.getType());
        dto.setManaCost(card.getManaCost());
        dto.setSetName(card.getSetName());
        dto.setImageUrl(card.getImageUrl());
        dto.setDescription(card.getDescription());
        dto.setMarketPrice(card.getMarketPrice());
        return dto;
    }
}
