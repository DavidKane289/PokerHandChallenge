package org.challenge;

import org.challenge.handlers.PokerHandsHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        new PokerHandsHandler().handle(args);
    }
}
