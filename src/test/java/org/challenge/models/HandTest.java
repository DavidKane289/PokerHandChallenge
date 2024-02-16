package org.challenge.models;

import org.challenge.exceptions.DuplicateCardException;
import org.challenge.exceptions.UnmatchedCardValueException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HandTest {

    @Test
    public void testHandCreationSimple() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "3H", "4H", "5H", "6H"};
        assertEquals(5, new Hand(handInput).getCards().length);
    }

    @Test()
    public void testDuplicateCardException() {
        String[] handInput = {"2H", "2H", "4H", "5H", "6H"};
        DuplicateCardException exception = assertThrows(DuplicateCardException.class, () -> new Hand(handInput));
        assertEquals("Hand contains duplicate card(s)", exception.getMessage());
    }

    @Test()
    public void testDuplicateCardsWithMixedCaseException() {
        String[] handInput = {"2H", "2D", "4H", "th", "TH"};
        DuplicateCardException exception = assertThrows(DuplicateCardException.class, () -> new Hand(handInput));
        assertEquals("Hand contains duplicate card(s)", exception.getMessage());
    }

    @Test()
    public void testTooFewCards() {
        String[] handInput = {"2H", "3H", "4H", "5H"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hand(handInput));
        assertEquals("Expected 5 Cards but received 4", exception.getMessage());
    }

    @Test()
    public void testTooManyCards() {
        String[] handInput = {"2H", "3H", "4H", "5H", "6H", "7H"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hand(handInput));
        assertEquals("Expected 5 Cards but received 6", exception.getMessage());
    }

    @Test()
    public void testHighCard() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "4C", "5H", "6H", "QD"};
        assertEquals(HandRank.HIGH_CARD, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForOnePair() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "4H", "5H", "6H", "2D"};
        assertEquals(HandRank.ONE_PAIR, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForTwoPair() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "2C", "4S", "4H", "TD"};
        assertEquals(HandRank.TWO_PAIR, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForThreeOfAKind() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "4H", "2S", "6H", "2D"};
        assertEquals(HandRank.THREE_OF_A_KIND, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testSortingForStraight() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"JH", "AC", "TS", "KH", "QD"};
        assertEquals(HandRank.STRAIGHT, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForAceLowStraight() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"AD", "2H", "3S", "4D", "5C"};
        assertEquals(HandRank.STRAIGHT, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForNormalStraight() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"6D", "7S", "8C", "9H", "TS"};
        assertEquals(HandRank.STRAIGHT, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForAceHighStraight() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"TH", "JH", "QC", "KS", "AS"};
        assertEquals(HandRank.STRAIGHT, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForFlush() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "3H", "9H", "TH", "KH"};
        assertEquals(HandRank.FLUSH, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForFullHouse() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"3H", "3S", "3D", "KS", "KH"};
        assertEquals(HandRank.FULL_HOUSE, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForFourOfAKind() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"2H", "2C", "2S", "6H", "2D"};
        assertEquals(HandRank.FOUR_OF_A_KIND, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForStraightFlush() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"JH", "TH", "9H", "8H", "7H"};
        assertEquals(HandRank.STRAIGHT_FLUSH, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForRoyalFlush() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"AH", "TH", "JH", "KH", "QH"};
        assertEquals(HandRank.ROYAL_FLUSH, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForAceNotHighAndLow() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"QH", "KD", "AH", "2C", "3S"};
        assertEquals(HandRank.HIGH_CARD, new Hand(handInput).determineHandRank());
    }

    @Test()
    public void testForAceCantBeHighAndLowButCanFlush() throws UnmatchedCardValueException, DuplicateCardException {
        String[] handInput = {"QH", "KH", "AH", "2H", "3H"};
        assertEquals(HandRank.FLUSH, new Hand(handInput).determineHandRank());
    }
}
