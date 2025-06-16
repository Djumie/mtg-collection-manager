package com.mtgcollector.controller;

import com.mtgcollector.dto.CardDto;
import com.mtgcollector.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

import static jdk.internal.jrtfs.JrtFileAttributeView.AttrID.size;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping
    public ResponseEntity<Page<CardDto>> getAllCards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size),
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Pageable pageable = PageRequest.of(page, size,
                sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending());
        Page<CardDto> cards = cardService.getAllCards(pageable);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) {
        try {
            CardDto card = cardService.getCardById(id);
            return ResponseEntity.ok(card);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CardDto>> searchCards(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String setName) {

        List<CardDto> cards = cardService.searchCards(name, type, setName);
        return ResponseEntity.ok(cards);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDto> updateCard(@PathVariable Long id, @RequestBody CardDto cardDto) {
        try {
            CardDto updatedCard = cardService.updateCard(id, cardDto);
            return ResponseEntity.ok(updatedCard);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
        try {
            CardDto createdCard = cardService.createCard(cardDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
