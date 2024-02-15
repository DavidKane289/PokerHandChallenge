package org.challenge.handlers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class PokerHandsHandlerTest {

    private ByteArrayOutputStream consoleContent;

    @BeforeEach
    void captureOutputStreams() {
        consoleContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleContent));
    }

    @AfterEach
    void resetOutputStreams() {
        System.setOut(null);
    }

    @Test
    public void testNullArguments() {
        PokerHandsHandler handler = new PokerHandsHandler();

        try {
            handler.handle(null);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testEmptyArguments() {
        String[] args = new String[1];
        PokerHandsHandler handler = new PokerHandsHandler();

        try {
            handler.handle(args);
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testTwoArguments() {
        String[] args = { "customFilePath", "customerFilePath2" };
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, (() -> new PokerHandsHandler().handle(args)));
        assertEquals("Too many arguments have been supplied", exception.getMessage());
    }

    @Test
    public void testMissingFile() {
        String[] args = {"missing"};
        PokerHandsHandler handler = new PokerHandsHandler();

        try {
            handler.handle(args);
            fail("Exception should have been thrown");
        } catch (Exception e) {
            assertInstanceOf(FileNotFoundException.class, e);
            assertEquals("File not found: missing", e.getMessage());
        }
    }

    @Test
    public void testEmptyFile() {
        String[] args = {"src/test/resources/empty-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();

        try {
            handler.handle(args);
            fail("Exception should have been thrown");
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("File is empty", e.getMessage());
        }
    }

    @Test
    public void testPoorlyFormattedLine() {
        String[] args = {"src/test/resources/poorly-formatted-lines-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();

        try {
            handler.handle(args);
            assertEquals("""
                Line #1 (6D-7H-8S-9C-TD) failed with exception: java.lang.IllegalArgumentException: Expected 5 Cards but received 1\r
                Line #2 (3S8H9C2S5C) failed with exception: java.lang.IllegalArgumentException: Expected 5 Cards but received 1\r
                Line #3 (6D 7H 8S 9C TD-KH) failed with exception: java.lang.IllegalArgumentException: Invalid input length, expected 2 but was 5\r
                """, consoleContent.toString());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testValidFile() {
        String[] args = {"src/test/resources/valid-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();
        try {
            handler.handle(args);
            assertEquals("3H JS 3C 7C 5D => One Pair\r\n", consoleContent.toString());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testFileWithBothSuccessfulLinesAndErrorLines() {
        String[] args = {"src/test/resources/mixed-results-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();
        try {
            handler.handle(args);
            assertEquals("""
                3H JS 3C 7C 5D => One Pair\r
                Line #2 (4H KS UC TH 5C) failed with exception: org.challenge.exceptions.UnmatchedCardValueException: Card Rank U was not able to be matched\r
                Line #3 (4H KS 2C TH 5X) failed with exception: org.challenge.exceptions.UnmatchedCardValueException: Card Suit X was not able to be matched\r
                Line #4 (4H KS 2C TH 5X2D) failed with exception: java.lang.IllegalArgumentException: Invalid input length, expected 2 but was 4\r
                Line #5 (4H KS 2C TH) failed with exception: java.lang.IllegalArgumentException: Expected 5 Cards but received 4\r
                Line #6 (4H KS 2C TH 4H) failed with exception: org.challenge.exceptions.DuplicateCardException: Hand contains duplicate card(s)\r
                2S 3S 6S 5S 4S => Straight Flush\r
                """, consoleContent.toString());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testFileWithUnicodeSuits() {
        String[] args = {"src/test/resources/unicode-suits-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();
        try {
            handler.handle(args);
            assertEquals("3♡ J♠ 3♣ 7♣ 5♢ => One Pair\r\n", consoleContent.toString());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    public void testFileWithLowerCaseData() {
        String[] args = {"src/test/resources/lower-case-data-file.txt"};
        PokerHandsHandler handler = new PokerHandsHandler();
        try {
            handler.handle(args);
            assertEquals("as ks qs js ts => Straight Flush\r\n", consoleContent.toString());
        } catch (Exception e) {
            fail("Exception should not have been thrown");
        }
    }
}
