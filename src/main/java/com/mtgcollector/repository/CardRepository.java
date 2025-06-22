package com.mtgcollector.repository;

import com.mtgcollector.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    // Find cards by name (case-insensitive partial matching)
    @Query("SELECT c FROM Card c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Card> findByNameContainingIgnoreCase(@Param("name") String name);

    // Find cards by type
    List<Card> findByTypeContainingIgnoreCase(String type);

    // Find cards by set name
    List<Card> findBySetNameIgnoreCase(String setName);

    // Complex search combining multiple criteria
    @Query("SELECT c FROM Card c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:type IS NULL OR LOWER(c.type) LIKE LOWER(CONCAT('%', :type, '%'))) AND " +
            "(:setName IS NULL OR LOWER(c.setName) LIKE LOWER CONCAT('%', :setName, '%')))")
    List<Card> searchCards(@Param("name") String name,
                           @Param("type") String type,
                           @Param("setName") String setName);

    //Find cards within a price range
    List<Card> findByMarketPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find the most expensive cards (useful for featured sections)
    List<Card> findTop10ByOrderByMarketPriceDesc();
}
