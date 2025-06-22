package com.mtgcollector.config;

import com.mtgcollector.entity.Card;
import com.mtgcollector.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public void run(String... args) throws Exception {
        if (cardRepository.count() == 0) {
            // Initialize with some sample cards
            Card card1 = new Card("Lightning Bolt", "Instant", new BigDecimal("2.99"));
            card1.setManaCost("{R}");
            card1.setSetName("Alpha");
            card1.setDescription("Lightning Bolt deals 3 damage to any target.");

            Card card2 = new Card("Black Lotus", "Artifact", new BigDecimal("50000.00"));
            card2.setManaCost("{0}");
            card2.setSetName("Alpha");
            card2.setDescription("{T}, Sacrifice Black Lotus: Add three mana of any one color.");

            cardRepository.saveAll(List.of(card1, card2));
        }
    }
}
