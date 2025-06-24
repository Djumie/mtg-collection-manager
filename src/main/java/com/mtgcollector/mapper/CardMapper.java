package com.mtgcollector.mapper;

import com.mtgcollector.dto.CardDto;
import com.mtgcollector.entity.Card;
import org.mapstruct.Mapper;

// @Mapper tells MapStruct this is a mapper interface.
// componentModel="spring" tells it to generate a Spring Bean to @Autowired it later
@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(Card card);
    Card toEntity(CardDto cardDto);

    void updateCardFromDto(CardDto cardDto, Card existingCard);

    Card toEntity(CardDto.CreateCardDto createCardDto);
}
