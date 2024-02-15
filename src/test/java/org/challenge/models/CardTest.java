package org.challenge.models;

import org.challenge.exceptions.UnmatchedCardValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {
    @Test()
    public void testUnknownCardRank() {
        UnmatchedCardValueException exception = assertThrows(UnmatchedCardValueException.class, () -> new Card("XH"));
        assertEquals("Card Rank X was not able to be matched", exception.getMessage());
    }

    @Test()
    public void testUnknownCardSuit() {
        UnmatchedCardValueException exception = assertThrows(UnmatchedCardValueException.class, () -> new Card("2X"));
        assertEquals("Card Suit X was not able to be matched", exception.getMessage());
    }

    @Test()
    public void testCardInputIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Card(null));
        assertEquals("Input is null", exception.getMessage());
    }

    @Test()
    public void testCardInputTooShort() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Card("2"));
        assertEquals("Invalid input length, expected 2 but was 1", exception.getMessage());
    }

    @Test()
    public void testCardInputTooLong() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Card("22222222222"));
        assertEquals("Invalid input length, expected 2 but was 11", exception.getMessage());
    }

    @Test()
    public void testCardInputHasUnicodeClubs() throws UnmatchedCardValueException {
        Card card = new Card("2♣");
        assertEquals(CardRank.TWO, card.getCardRank());
        assertEquals(CardSuit.CLUBS, card.getCardSuit());
    }

    @Test()
    public void testCardInputHasUnicodeDiamonds() throws UnmatchedCardValueException {
        Card card = new Card("2♢");
        assertEquals(CardRank.TWO, card.getCardRank());
        assertEquals(CardSuit.DIAMONDS, card.getCardSuit());
    }

    @Test()
    public void testCardInputHasUnicodeHearts() throws UnmatchedCardValueException {
        Card card = new Card("2♡");
        assertEquals(CardRank.TWO, card.getCardRank());
        assertEquals(CardSuit.HEARTS, card.getCardSuit());
    }

    @Test()
    public void testCardInputHasUnicodeSpades() throws UnmatchedCardValueException {
        Card card = new Card("2♠");
        assertEquals(CardRank.TWO, card.getCardRank());
        assertEquals(CardSuit.SPADES, card.getCardSuit());
    }
}
