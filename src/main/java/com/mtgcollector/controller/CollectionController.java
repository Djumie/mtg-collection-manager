package com.mtgcollector.controller;

import com.mtgcollector.dto.CollectionEntryDto;
import com.mtgcollector.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('USER')")        // All endpoints require user authentication
public class CollectionController {

    private final CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<CollectionEntryDto>> getUserCollection (
            Authentication authentication) {

        String username = authentication.getName();
        List<CollectionEntryDto> collection = collectionService.getUserCollection(username);
        return ResponseEntity.ok(collection);
    }

    @PostMapping
    public ResponseEntity<CollectionEntryDto> addCardToCollection(
            @Valid @RequestBody CollectionEntryDto.AddCardToCollectionDto dto,
            Authentication authentication) {
        // Get current user from JWT token
        String username = authentication.getName();
        CollectionEntryDto entry = collectionService.addCardToCollection(username, cardId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);   // 201 Content for successful creation
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> removeCardFromCollection(
            @PathVariable Long cardId,
            Authentication authentication) {

        String username = authentication.getName();
        collectionService.removeCardFromCollection(username, cardId);
        return ResponseEntity.noContent().build();      // 204 No Content for successful deletion
    }

    @PutMapping("/{cardId}")
    public ResponseEntity<CollectionEntryDto> updateCollectionEntry (
            @PathVariable Long cardId,
            @Valid @RequestBody CollectionEntryDto.AddCardToCollectionDto dto,
            Authentication authentication) {

        String username = authentication.getName();
        CollectionEntryDto updated = collectionService.addCardToCollection(username, cardId, dto);
        return ResponseEntity.ok(updated);  // 200 Content for successful retrieval/update
    }

    // Single Constructor

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }
}
