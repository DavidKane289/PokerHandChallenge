package org.challenge.models;

public enum CardSuit {
    CLUBS("C", '♣'),
    DIAMONDS("D", '♢'),
    HEARTS("H", '♡'),
    SPADES("S", '♠');

    private final String text;

    private final String unicodeChar;

    CardSuit(String text, char unicodeChar) {
        this.text = text;
        this.unicodeChar = String.valueOf(unicodeChar);
    }

    public static CardSuit findByText(String text) {
        for(CardSuit cs : values()) {
            if(cs.text.equals(text) || cs.unicodeChar.equals(text)) {
                return cs;
            }
        }

        return null;
    }
}
