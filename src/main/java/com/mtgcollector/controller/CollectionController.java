package com.mtgcollector.controller;

import com.mtgcollector.dto.AddCardToCollectionDto;
import com.mtgcollector.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@PreAuthorize("hasRole('USER')")        // All endpoints require user authentication
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @PostMapping
    public ResponseEntity<CollectionEntryDto> addCardToCollection(
            @Valid @RequestBody AddCardToCollectionDto dto,
            Authentication authentication) {
        // Get current user from JWT token
        String username = authentication.getName();
        CollectionEntryDto entry = collectionService.addCardToCollection(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(entry);   // 201 Content for successful creation
    }

    @GetMapping
    public ResponseEntity<List<CollectionEntryDto>> getUserCollection (
            Authentication authentication) {

        String username = authentication.getName();
        List<CollectionEntryDto> collection = collectionService.getUserCollection(username);
        return ResponseEntity.ok(collection);
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
            @Valid @RequestBody UpdateCollectionEntryDto dto,
            Authentication authentication) {

        String username = authentication.getName();
        CollectionEntryDto updated = collectionService.updateCollectionEntry(username, cardId, dto);
        return ResponseEntity.ok(updated);  // 200 Content for successful retrieval/update
    }
}
