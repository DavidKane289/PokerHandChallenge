package org.challenge.models;

public enum CardRank {
    ONE("1", 0),
    TWO("2", 1),
    THREE("3", 2),
    FOUR("4", 3),
    FIVE("5", 4),
    SIX("6", 5),
    SEVEN("7", 6),
    EIGHT("8", 7),
    NINE("9", 8),
    TEN("T", 9),
    JACK("J", 10),
    QUEEN("Q", 11),
    KING("K", 12),
    ACE("A", 13);

    private final String text;
    private final int index;

    CardRank(String text, int index) {
        this.text = text;
        this.index = index;
    }

    public static CardRank findByText(String text) {
        for(CardRank cr : values()) {
            if(cr.text.equals(text)) {
                return cr;
            }
        }

        return null;
    }

    public int getIndex() {
        return index;
    }
}
