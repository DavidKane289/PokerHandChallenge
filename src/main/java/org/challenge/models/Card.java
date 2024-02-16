package org.challenge.models;

import org.challenge.exceptions.UnmatchedCardValueException;

/**
 * Class that represents a standard playing card
 * Provides access to a cards rank and suit
 */
public class Card {
    private final CardRank rank;
    private final CardSuit suit;

    /**
     * Constructs a card object with a rank and suit derived from the input and matched to known values.
     *
     * @param input - The card value as a string, i.e. 2H (2 of Hearts)
     * @throws UnmatchedCardValueException - throws up to parent for handling if matchCardValue throws the error
     */
    public Card(String input) throws IllegalArgumentException, UnmatchedCardValueException {
        if (input == null) {
            throw new IllegalArgumentException("Input is null");
        }

        if(input.length() != 2) {
            throw new IllegalArgumentException("Invalid card length, expected 2 characters but was " + input.length());
        }

        this.rank = CardRank.findByText(String.valueOf(Character.toUpperCase(input.charAt(0))));
        if(this.rank == null) {
            throw new UnmatchedCardValueException("Card Rank " + input.charAt(0) + " was not able to be matched");
        }

        this.suit = CardSuit.findByText(String.valueOf(Character.toUpperCase(input.charAt(1))));
        if(this.suit == null) {
            throw new UnmatchedCardValueException("Card Suit " + input.charAt(1) + " was not able to be matched");
        }
    }

    public CardRank getCardRank() {
        return rank;
    }

    public int getCardRankIndex() {
        return this.rank.getIndex();
    }

    public CardSuit getCardSuit() {
        return suit;
    }
}
