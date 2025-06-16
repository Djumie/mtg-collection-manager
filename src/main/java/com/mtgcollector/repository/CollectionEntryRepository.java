package com.mtgcollector.repository;

import com.mtgcollector.model.Card;
import com.mtgcollector.model.CollectionEntry;
import com.mtgcollector.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionEntryRepository extends JpaRepository<CollectionEntry, Long> {

    // Find all cards in a user's collection, ordered by when they were added
    List<CollectionEntry> findByUserOrderByAddedAtDesc(User user);

    // Find a specific card in a user's collection
    Optional<CollectionEntry> findByUserAndCard(User user, Card card);

    // Count how many different cards a user owns
    @Query("SELECT COUNT(DISTINCT c.card) FROM CollectionEntry c WHERE c.user = :user")
    Long countDistinctCardsByUser(@Param("user") User user);

    // Calculate total value of a user's collection
    @Query("SELECT SUM(c.quantity * c.card.marketPrice) FROM CollectionEntry c WHERE c.user = :user")
    BigDecimal calculateCollectionValue(@Param("user") User user);

    // Find users who own a specific card (useful for trading features)
    @Query("SELECT c.user FROM CollectionEntry c WHERE c.card = :card")
    List<User> findUsersWhoOwnCard(@Param("card") Card card);

    // Find Collection entries by condition
    List<CollectionEntry> findByUserAndCondition(User user, String condition);
}
