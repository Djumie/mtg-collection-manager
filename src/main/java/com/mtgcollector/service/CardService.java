package com.mtgcollector.service;

import com.mtgcollector.dto.CardDto;
import com.mtgcollector.entity.Card;
import com.mtgcollector.entity.CollectionEntry;
import com.mtgcollector.exception.CardNotFoundException;
import com.mtgcollector.repository.CardRepository;
import com.mtgcollector.repository.CollectionEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class CardService {

    private final CardRepository cardRepository;

    private final CollectionEntryRepository collectionEntryRepository;

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

    public CardDto getCardById(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
        return convertToDto(card);
    }

    @Transactional
    public CardDto createCard(CardDto.CreateCardDto createCardDto) {
        Card card = new Card();
        card.setName(createCardDto.getName());
        card.setType(createCardDto.getType());
        card.setManaCost(createCardDto.getManaCost());
        card.setSetName(createCardDto.getSetName());
        card.setImageUrl(createCardDto.getImageUrl());
        card.setDescription(createCardDto.getDescription());
        card.setMarketPrice(createCardDto.getMarketPrice());

        card = cardRepository.save(card);
        return convertToDto(card);
    }

    public List<CardDto> searchCardsForUser(String name, String type, String setName, String username, boolean inCollectionOnly) {
        List<Card> cards;

        if (inCollectionOnly && username != null) {
            // This would require a custom query in the repository
            cards = cardRepository.searchCards(name, type, setName);
        } else {
            cards = cardRepository.searchCards(name, type, setName);
        }

        return cards.stream()
                .map(card -> convertToDtoWithUserContext(card, username))
                .collect(Collectors.toList());
    }

    public CardDto updateCard(Long id, CardDto cardDto) {
        return cardDto;
    }

    // Advanced search with multiple criteria and current user context
    private CardDto convertToDtoWithUserContext(Card card, String username) {
        CardDto dto = convertToDto(card);

        if (username != null) {
            // Check if current user owns this card
            Optional<CollectionEntry> entry = collectionEntryRepository
                    .findByUserUsernameAndCard(username, card);

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

    // Single constructor - Spring will autowire it (no @Autowired needed in Spring 4.3+)

    public CardService(CardRepository cardRepository, CollectionEntryRepository collectionEntryRepository) {
        this.cardRepository = cardRepository;
        this.collectionEntryRepository = collectionEntryRepository;
    }
}
