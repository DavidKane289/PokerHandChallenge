package org.challenge.models;

import org.challenge.exceptions.DuplicateCardException;
import org.challenge.exceptions.UnmatchedCardValueException;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Class representing a hand of cards.
 * Provides access to the determineHandRank() method which provides the appropriate rank based on it's cards
 */
public class Hand {
    private final Card[] cards = new Card[5];

    public Hand(String[] input) throws IllegalArgumentException, DuplicateCardException, UnmatchedCardValueException {
        if(input.length != 5) {
            throw new IllegalArgumentException("Expected 5 Cards but received " + input.length);
        }

        if(new HashSet<>(Arrays.stream(input).map(String::toUpperCase).collect(Collectors.toList())).size() != input.length) {
            throw new DuplicateCardException("Hand contains duplicate card(s)");
        }

        for (int i = 0; i < input.length; i++) {
            this.cards[i] = new Card(input[i]);
        }

        Arrays.sort(this.cards, Comparator.comparing(Card::getCardRankIndex));
    }

    /**
     * Uses the private functions of this class to determine the correct HandRank for the card array
     * @return HandRank - An enum representing the value of the cards in the hand
     */
    public HandRank determineHandRank() {
        long countOfRepeatingCardsByRank = countTotalRepeatingCards(Card::getCardRank);
        boolean isFlush = countTotalRepeatingCards(Card::getCardSuit) == cards.length;
        boolean isStraight = isStraight();
        boolean isTwoPair = isTwoPair();
        boolean isFullHouse = isTwoPair && countOfRepeatingCardsByRank == 5;

        if(isStraight && isFlush) {
            return HandRank.STRAIGHT_FLUSH;
        }

        if(!isTwoPair && countOfRepeatingCardsByRank == 4) {
            return HandRank.FOUR_OF_A_KIND;
        }

        if(isFullHouse) {
            return HandRank.FULL_HOUSE;
        }

        if(isFlush) {
            return HandRank.FLUSH;
        }

        if(isStraight) {
            return HandRank.STRAIGHT;
        }

        if(countOfRepeatingCardsByRank == 3) {
            return HandRank.THREE_OF_A_KIND;
        }

        if(isTwoPair && countOfRepeatingCardsByRank >= 4) {
            return HandRank.TWO_PAIR;
        }

        if(countOfRepeatingCardsByRank == 2) {
            return HandRank.ONE_PAIR;
        }

        return HandRank.HIGH_CARD;
    }

    /**
     * Used to total the number of repeating ranks
     * @param classifier - The function to use to determine equality i.e. rank, suit
     * @return long - The total number of cards which are repeated i.e. 4 (2S, 2D, 2C, 2H, 5H)
     */
    private long countTotalRepeatingCards(Function<Card, ?> classifier) {
        return Arrays.stream(cards)
                .collect(Collectors.groupingBy(classifier, Collectors.counting()))
                .values().stream()
                .filter(count -> count > 1)
                .mapToLong(Long::longValue)
                .sum();
    }

    /**
     * Returns boolean to say if the cards are a straight or not
     * @return boolean
     */
    private boolean isStraight() {
        List<Integer> ranks = Arrays.stream(cards).map(Card::getCardRankIndex).collect(Collectors.toList());

        boolean hasAce = false;
        int requiredRanksInSequence = 4;
        if (ranks.contains(CardRank.ACE.getIndex())) {
            ranks.remove(Integer.valueOf(CardRank.ACE.getIndex()));
            hasAce = true;
            --requiredRanksInSequence;
        }

        long totalInSequence = 0;
        for (int i = 0; i < ranks.size() - 1; i++) {
            if (ranks.get(i) + 1 != ranks.get(i + 1)) {
                return false;
            }

            totalInSequence++;
        }

        if(hasAce && totalInSequence == requiredRanksInSequence) {
            return ranks.get(0) == 1 || ranks.get(3) == 12;
        }

        return !hasAce && totalInSequence == requiredRanksInSequence;
    }

    /**
     * Used to identify when two pairs or cards of the same rank exist in the hand
     * @return boolean - returns true when count reads two meaning two pairs i.e. (AH, AC, 5D, 3C, 3S) or false when one or no pairs exist
     */
    private boolean isTwoPair() {
        return Arrays.stream(cards)
                .collect(Collectors.groupingBy(Card::getCardRank, Collectors.counting()))
                .values().stream()
                .filter(count -> count > 1)
                .count() == 2;
    }

    public Card[] getCards() {
        return cards;
    }
}
