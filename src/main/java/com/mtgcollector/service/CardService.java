package com.mtgcollector.service;

import com.mtgcollector.dto.CardDto;
import com.mtgcollector.entity.Card;
import com.mtgcollector.entity.CollectionEntry;
import com.mtgcollector.exception.CardNotFoundException;
import com.mtgcollector.mapper.CardMapper;
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

    private final CardRepository cardRepository;
    private final CollectionEntryRepository collectionEntryRepository;
    private final CardMapper cardMapper;

    @Autowired
    public CardService(CardRepository cardRepository, CollectionEntryRepository collectionEntryRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.collectionEntryRepository = collectionEntryRepository;
        this.cardMapper = cardMapper;
    }

    public Page<CardDto> searchCards(String name, String type, Pageable pageable) {
        Page<Card> cards = cardRepository.searchCards(name, type, pageable);
        return cards.map(cardMapper::toDto);
    }

    public Page<CardDto> getAllCards(Pageable pageable) {
        Page<Card> cards = cardRepository.findAll(pageable);
        return cards.map(cardMapper::toDto);
    }

    public List<CardDto> searchCards(String name, String type, String setName) {
        List<Card> cards = cardRepository.searchCards(name, type, setName);
        return cards.stream().map(cardMapper::toDto)
                .collect(Collectors.toList());
    }

    public CardDto getCardById(Long id) {
        Card card = cardRepository.findById(id).orElseThrow(() -> new CardNotFoundException("Card not found with id: " + id));
        return cardMapper.toDto(card);
    }

    @Transactional
    public CardDto createCard(CardDto.CreateCardDto createCardDto) {
        Card card = cardMapper.toEntity(createCardDto);
        Card savedCard = cardRepository.save(card);
        return cardMapper.toDto(savedCard);
    }

    public List<CardDto> searchCardsForUser(String name, String type, String setName, String username, boolean inCollectionOnly) {
        List<Card> cards;

        if (inCollectionOnly && username != null) {
            // This would require a custom query in the repository
            List<CollectionEntry> entries = collectionEntryRepository.findUserCollectionByCardCriteria(username, name, type, setName);
            cards = entries.stream()
                    .map(CollectionEntry::getCard)
                    .collect(Collectors.toList());
        } else {
            cards = cardRepository.searchCards(name, type, setName);
        }

        return cards.stream()
                .map(card -> convertToDtoWithUserContext(card, username))
                .collect(Collectors.toList());
    }

    public CardDto updateCard(Long id, CardDto cardDto) {
        Card existingCard = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException("Cannot Update. Card not found with id: " + id));
        cardMapper.updateCardFromDto(cardDto, existingCard);
        Card updateCard = cardRepository.save(existingCard);
        return cardMapper.toDto(updateCard);
    }
    // Advanced search with multiple criteria and current user context
    private CardDto convertToDtoWithUserContext(Card card, String username) {
        CardDto dto = cardMapper.toDto(card);

        if (username != null) {
            // Check if current user owns this card
            Optional<CollectionEntry> entry = collectionEntryRepository.findByUser_UsernameAndCard_Id(username, card.getId());

            if (entry.isPresent()) {
                dto.setInCollection(true);
                dto.setOwnedQuantity(entry.get().getQuantity());
            } else {
                dto.setInCollection(false);
                dto.setOwnedQuantity(0);
            }
        }
        return dto;
    }
}
